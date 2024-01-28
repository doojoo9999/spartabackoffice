package com.teamsparta.spartabackoffice.infra.util

import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtils {
    fun getAuthenticatedUser(): UserPrincipal {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication.principal !is UserPrincipal) {
            throw IllegalArgumentException("알 수 없는 사용자 타입입니다.")
        }
        return authentication.principal as UserPrincipal
    }

    fun validatePlatform(userPrincipal: UserPrincipal, platform: String) {
        if (userPrincipal.platform != platform) {
            throw IllegalArgumentException("플랫폼 정보가 일치하지 않습니다.")
        }
    }
}