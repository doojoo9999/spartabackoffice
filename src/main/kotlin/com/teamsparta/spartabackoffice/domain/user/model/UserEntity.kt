package com.teamsparta.spartabackoffice.domain.user.model

import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "name", nullable = false)
    var name: String,


) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    //var id: Long = 0
    @Column(name = "role", nullable = false)
    var role: String = "USER"
}

fun UserEntity.toResponse() : UserResponse {
    return UserResponse(
        id = id,
        email = email,
        name = name,
        role = role
    )
}