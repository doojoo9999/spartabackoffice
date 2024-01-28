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
        val email = oAuth2User.attributes.get("email").toString()

        val platform = Platform.GOOGLE
        val member = if(!socialRepository.existsByEmail(email)) {
            val newMember = SocialEntity(
                email = email,
                role = UserRole.STUDENT,
                platform = platform
            )
            socialRepository.save(newMember)
            newMember
        } else {
            socialRepository.findByEmail(email)!!
        }

        return jwtProvider.generateJwtDto(oAuth2User, member.id.toString(), member.role.name, platform.name)
    }

}
