package com.teamsparta.spartabackoffice.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Long,
    val email: String,
    val authorities : Collection<GrantedAuthority>,
    val platform: String

) {
    constructor(id: Long, email: String, roles: Set<String>, platform: String) : this(
        id,
        email,
        roles.map { SimpleGrantedAuthority("ROLE_$it")},
        platform
    )

}
