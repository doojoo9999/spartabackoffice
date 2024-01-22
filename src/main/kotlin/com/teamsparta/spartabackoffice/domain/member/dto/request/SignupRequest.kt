package com.teamsparta.spartabackoffice.domain.member.dto.request

data class SignUpRequest (
    var email: String,
    val password: String,
    val name: String,
    val role: String
)