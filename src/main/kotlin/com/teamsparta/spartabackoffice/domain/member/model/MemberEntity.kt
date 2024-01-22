package com.teamsparta.spartabackoffice.domain.member.model

import com.teamsparta.spartabackoffice.domain.member.dto.response.MemberResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
class MemberEntity(
    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "role", nullable = false)
    var role: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}

fun MemberEntity.toResponse() : MemberResponse {
    return MemberResponse(
        id = id,
        email = email,
        name = name,
        role = role
    )
}