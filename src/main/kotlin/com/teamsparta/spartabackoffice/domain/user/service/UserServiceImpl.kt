package com.teamsparta.spartabackoffice.domain.user.service

import com.teamsparta.spartabackoffice.domain.user.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.domain.user.model.UserEntity
import com.teamsparta.spartabackoffice.domain.user.repository.UserRepository
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import com.teamsparta.spartabackoffice.infra.security.jwt.JwtPlugin
import com.teamsparta.spartabackoffice.infra.util.ValidationUtil
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {
    override fun signUp(request: SignUpRequest): UserResponse {
        userRepository.findByEmail(request.email)?.let {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
        if (!ValidationUtil.isValidUsername(request.name)) throw IllegalArgumentException("사용자 이름은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.")
        if (!ValidationUtil.isValidPassword(request.password)) throw IllegalArgumentException("비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 합니다.")

        val user = UserEntity(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name
        )
        userRepository.save(user)
        return user.toResponse()
    }

    override fun login(request: LoginRequest): Pair<UserResponse, String> {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 또는 비밀번호가 다릅니다.")
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("이메일 또는 비밀번호가 다릅니다.")
        }
        val token = jwtPlugin.generateAccessToken(user.id.toString(), user.email, user.role.toString())
        return Pair(user.toResponse(), token)
    }

    override fun getUser(userId: Long): UserResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        val id = when (val principal = authentication.principal) {
            is UserPrincipal -> principal.id
            is OAuth2User -> (principal.attributes["sub"] as String).toLong()
            else -> throw IllegalArgumentException("알 수 없는 사용자 타입입니다.")
        }

        if (id != userId) {
            throw IllegalArgumentException("요청한 사용자 ID와 토큰의 사용자 ID가 일치하지 않습니다.")
        }
        val user = userRepository.findById(id).orElseThrow {
            IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        }
        return user.toResponse()
    }

    override fun deleteUser(userId: Long) {
        val authentication = SecurityContextHolder.getContext().authentication
        val userPrincipal = authentication.principal as UserPrincipal

        if (userPrincipal.id != userId) {
            throw IllegalArgumentException("요청한 사용자 ID와 토큰의 사용자 ID가 일치하지 않습니다.")
        }
        userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        }.also {
            userRepository.delete(it)
        }
    }
}