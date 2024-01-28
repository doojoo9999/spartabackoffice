package com.teamsparta.spartabackoffice.infra.social

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

//class SocialAuthentication(
//    private val oAuth2User: OAuth2User
//) : Authentication {
//    override fun getName(): String = oAuth2User.name
//    override fun getAuthorities(): Collection<GrantedAuthority> = oAuth2User.authorities
//    override fun getCredentials(): Any = oAuth2User.attributes
//    override fun getDetails(): Any = oAuth2User.attributes
//    override fun getPrincipal(): Any = oAuth2User
//    override fun isAuthenticated(): Boolean = true
//    override fun setAuthenticated(isAuthenticated: Boolean) = throw UnsupportedOperationException()
//}