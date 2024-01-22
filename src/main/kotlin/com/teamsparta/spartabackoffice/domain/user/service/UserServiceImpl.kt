package com.teamsparta.spartabackoffice.domain.user.service

import com.teamsparta.spartabackoffice.domain.user.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.domain.user.model.UserEntity
import com.teamsparta.spartabackoffice.domain.user.model.toResponse
import com.teamsparta.spartabackoffice.domain.user.repository.UserRepository
import com.teamsparta.spartabackoffice.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {
    @Transactional
    override fun signUp(request: SignUpRequest): UserResponse {
        val member = UserEntity(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name
        )
        userRepository.save(member)
        return member.toResponse()
    }
    @Transactional
    override fun login(request: LoginRequest): Pair<UserResponse, String> {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 또는 비밀번호가 다릅니다.")
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("이메일 또는 비밀번호가 다릅니다.")
        }
        val token = jwtPlugin.generateAccessToken(user.id?.toString() ?: "unknown", user.email, user.role)
        //val token = jwtPlugin.generateAccessToken(user.id.toString(), user.email, user.role)
        return Pair(user.toResponse(), token)
    }
}