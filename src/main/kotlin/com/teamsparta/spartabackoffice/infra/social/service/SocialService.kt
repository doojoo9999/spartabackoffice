package com.teamsparta.spartabackoffice.infra.social.service

import com.teamsparta.spartabackoffice.domain.exception.EmailNotFoundException
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
    fun login(oAuth2User: OAuth2User) : JwtDto {
        //TODO: 1. 회원이 아니라면 회원 가입을 시켜준다.
        if(!socialRepository.existsByEmail(oAuth2User.attributes["email"] as String)) {
            val member = SocialEntity(
                email = oAuth2User.attributes["email"] as String,
                role = UserRole.ROLE_student
            )
            socialRepository.save(member)
        }

        //TODO: 2. token 을 생성해준다.
        return jwtProvider.generateJwtDto(oAuth2User)
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
