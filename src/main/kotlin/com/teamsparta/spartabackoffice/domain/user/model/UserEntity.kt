package com.teamsparta.spartabackoffice.domain.user.model

import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    email: String,
    password: String,
    name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    //var id: Long? = null
    @Column(nullable = false)
    var email = email

    @Column(nullable = false)
    var password = password

    @Column(nullable = false)
    var name = name

    @Column(nullable = false)
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