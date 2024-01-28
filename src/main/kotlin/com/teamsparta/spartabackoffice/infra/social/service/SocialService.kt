package com.teamsparta.spartabackoffice.infra.social.service

import com.teamsparta.spartabackoffice.domain.exception.EmailNotFoundException
import com.teamsparta.spartabackoffice.domain.user.model.Platform
import com.teamsparta.spartabackoffice.domain.user.model.UserRole
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import com.teamsparta.spartabackoffice.infra.social.dto.SocialResponse
import com.teamsparta.spartabackoffice.infra.social.jwt.JwtDto
import com.teamsparta.spartabackoffice.infra.social.jwt.JwtProvider
import com.teamsparta.spartabackoffice.infra.social.model.SocialEntity
import com.teamsparta.spartabackoffice.infra.social.repository.SocialRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SocialService(
    private val socialRepository: SocialRepository,
    private val jwtProvider: JwtProvider
) {

    @Transactional
    fun socialLogin(oAuth2User: OAuth2User) : JwtDto {
        val email = oAuth2User.attributes["email"] as String
        val platform = Platform.GOOGLE
        val member = if(!socialRepository.existsByEmail(email)) {
            val newMember = SocialEntity(
                email = email,
                role = UserRole.ROLE_student,
                platform = platform
            )
            socialRepository.save(newMember)
            newMember
        } else {
            socialRepository.findByEmail(email)!!
        }

        return jwtProvider.generateJwtDto(oAuth2User, member.id.toString(), member.role.name, platform.name)
    }

    fun getSocialUser(socialId: Long): SocialResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        if(authentication.principal !is UserPrincipal)
            throw IllegalArgumentException("알 수 없는 사용자 타입입니다.")

        val userPrincipal = authentication.principal as UserPrincipal
        val email = userPrincipal.email
        val socialUser = socialRepository.findById(socialId)
            .orElseThrow { EmailNotFoundException(email) }
        return socialUser.toResponse()
    }
}