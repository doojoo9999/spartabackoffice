package com.teamsparta.spartabackoffice.domain.user.dto.request

data class LoginRequest (
    val email: String,
    var password: String
)