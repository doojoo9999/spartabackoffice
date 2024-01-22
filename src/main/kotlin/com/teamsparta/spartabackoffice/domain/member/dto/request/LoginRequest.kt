package com.teamsparta.spartabackoffice.domain.member.dto.request

data class LoginRequest (
    val email: String,
    var password: String
)