package com.teamsparta.spartabackoffice.infra.security.jwt

import com.teamsparta.spartabackoffice.domain.user.model.Platform
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter (
    private val jwtPlugin : JwtPlugin
): OncePerRequestFilter() {

    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if (jwt != null) {
            jwtPlugin.validateToken(jwt)
                .onSuccess {
                    val userId = it.payload.subject.toLong()
                    val role = it.payload.get("role", String::class.java)
                    val email = it.payload.get("email", String::class.java)
                    val platform = it.payload.get("platform", String::class.java)
                    val principal = UserPrincipal(
                        id = userId,
                        email = email,
                        roles = setOf("ROLE_$role"),
                        platform = platform
                    )
                    val authentication = JwtAuthenticationToken (
                        principal = principal,
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                }
                .onFailure {
                    // 토큰이 유효하지 않는 경우, 에러 메시지 출력
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token")
                    return
                }
        }

        filterChain.doFilter(request, response)

    }

    private fun HttpServletRequest.getBearerToken(): String? {
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null // Bearer {JWT}
        return BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)
    }

}