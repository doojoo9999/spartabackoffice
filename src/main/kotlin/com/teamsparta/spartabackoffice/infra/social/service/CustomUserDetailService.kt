package com.teamsparta.spartabackoffice.infra.social.service

import com.teamsparta.spartabackoffice.domain.user.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    private val userRepository: UserRepository
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        return super.loadUser(userRequest)
    }
    fun loadUserByEmail(email: String): OAuth2User {
        val userEntity = userRepository.findByEmail(email)
        if (userEntity != null) {
            val authorities = mutableListOf<SimpleGrantedAuthority>()
            authorities.add(SimpleGrantedAuthority(userEntity.role.toString()))
            return DefaultOAuth2User(
                authorities,
                mapOf(
                    "email" to userEntity.email,
                    "name" to userEntity.name,
                    "role" to userEntity.role
                ),
                "name"
            )
        }
        throw UsernameNotFoundException("$email not found")
    }
}