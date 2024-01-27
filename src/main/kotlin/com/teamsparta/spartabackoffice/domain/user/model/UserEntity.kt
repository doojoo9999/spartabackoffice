package com.teamsparta.spartabackoffice.domain.user.model

import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.infra.social.role.AuthType
import jakarta.persistence.*

@Entity(name="users")
class UserEntity(
    var email: String,
    var password: String?,
    var name: String?,
    @Enumerated(EnumType.STRING)
    var role: AuthType = AuthType.student
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun toResponse() : UserResponse {
        return UserResponse(
            id = id,
            email = email,
            name = name,
            role = role.name
        )
    }
}