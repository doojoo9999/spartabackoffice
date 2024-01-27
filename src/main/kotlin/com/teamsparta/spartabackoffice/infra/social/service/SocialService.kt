package com.teamsparta.spartabackoffice.infra.social.service

import com.teamsparta.spartabackoffice.domain.user.model.UserEntity
import com.teamsparta.spartabackoffice.domain.user.model.UserRole
import com.teamsparta.spartabackoffice.domain.user.repository.UserRepository
import com.teamsparta.spartabackoffice.infra.social.jwt.JwtDto
import com.teamsparta.spartabackoffice.infra.social.jwt.JwtProvider
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SocialService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val customUserDetailService: CustomUserDetailService
) {

    @Transactional
    fun socialLogin(oAuth2User: OAuth2User) : JwtDto {
        val email = oAuth2User.attributes["email"] as String
        val role = UserRole.ROLE_student
        if(!userRepository.existsByEmail(oAuth2User.attributes["email"] as String)) {
            val user = UserEntity(
                email = email,
                password = "",
                name = "",
                role = role
            )
            userRepository.save(user)
        }

        return jwtProvider.generateJwtDto(oAuth2User)
    }
    fun verifyToken(token: String): OAuth2User? {
        val result = jwtProvider.validateToken(token)
        return if (result.isSuccess) {
            val email = result.getOrNull()?.payload?.subject
            if (email != null) {
                customUserDetailService.loadUserByEmail(email)
            } else {
                null
            }
        } else {
            // 토큰 검증 실패 시 null 반환
            null
        }
    }
}