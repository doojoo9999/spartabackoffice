package com.teamsparta.spartabackoffice.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

data class GoogleUser(
    private val attributes: Map<String, Any>,
    private val authorities: Collection<GrantedAuthority>
) : OAuth2User {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getAttributes(): Map<String, Any> {
        return attributes
    }

    override fun getName(): String {
        return attributes["sub"] as String
    }
}

// 생성자
fun createGoogleUser(attributes: Map<String, Any>, roles: Set<String>): GoogleUser {
    val authorities = roles.map { SimpleGrantedAuthority("ROLE_$it") }
    return GoogleUser(attributes, authorities)
}