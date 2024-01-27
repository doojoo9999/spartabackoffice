package com.teamsparta.spartabackoffice.infra.security.jwt

import com.teamsparta.spartabackoffice.infra.social.SocialAuthentication
import com.teamsparta.spartabackoffice.infra.social.service.SocialService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class SocialLoginFilter(
    authenticationManager: AuthenticationManager,
    private val socialService: SocialService

) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substring(7)
            val oAuth2User = socialService.verifyToken(token)

            if (oAuth2User != null) {
                val authentication = SocialAuthentication(oAuth2User)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        chain.doFilter(request, response)
    }
}