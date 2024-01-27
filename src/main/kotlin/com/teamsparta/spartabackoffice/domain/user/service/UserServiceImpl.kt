package com.teamsparta.spartabackoffice.domain.user.service

import com.teamsparta.spartabackoffice.domain.exception.ModelNotFoundException
import com.teamsparta.spartabackoffice.domain.user.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdatePasswordRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdateUserRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.domain.user.model.UserEntity
import com.teamsparta.spartabackoffice.domain.user.model.toResponse
import com.teamsparta.spartabackoffice.domain.user.repository.UserRepository
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import com.teamsparta.spartabackoffice.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {
    override fun signUp(request: SignUpRequest): UserResponse {
        userRepository.findByEmail(request.email)?.let {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
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
        val token = jwtPlugin.generateAccessToken(user.id.toString(), user.email, user.role)
        return Pair(user.toResponse(), token)
    }

    override fun getUser(userId: Long): UserResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        val userPrincipal = authentication.principal as UserPrincipal
        if (userPrincipal.id != userId) {
            throw IllegalArgumentException("요청한 사용자 ID와 토큰의 사용자 ID가 일치하지 않습니다.")
        }
        val user = userRepository.findById(userPrincipal.id).orElseThrow {
            IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        }
        return user.toResponse()
    }


    @Transactional
    override fun updateUser(
        userId: Long,
        request: UpdateUserRequest
    ): UpdateUserResponse {

        val user = userRepository.findByIdOrNull(userId)
                ?: throw IllegalArgumentException("유저 정보를 다시 확인해 주세요.")

            user.email = request.email

            userRepository.save(user)

            return UpdateUserResponse(user.email)

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

    override fun updatePassword(userPrincipal: UserPrincipal, request: UpdatePasswordRequest): Any {
        val user = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException ("UserId", userPrincipal.id)

        if (!passwordEncoder.matches(request.oldPassword, user.password)) {
            throw IllegalArgumentException ("비밀번호가 틀렸습니다.")
        }

        if (passwordEncoder.matches(request.newPassword, request.confirmPassword)) {
            throw IllegalArgumentException("새 비밀번호와 확인 비밀번호가 다릅니다.")
        }

        if (user.oldPasswords.any { passwordEncoder.matches(request.newPassword, it)}) {
            throw IllegalStateException("3회 안에 사용되었던 비밀번호를 재사용할 수 없습니다.")
        }

        user.password = passwordEncoder.encode(request.newPassword)
        user.oldPasswords.add(user.password)

        if (user.oldPasswords.size > 3) {
            user.oldPasswords.removeAt(0)
        }

        userRepository.save(user)

        return "비밀번호 변경 완료"
    }


}