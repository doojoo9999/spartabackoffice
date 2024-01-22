package com.teamsparta.spartabackoffice.domain.user.dto.request

data class SignUpRequest (
    var email: String,
    val password: String,
    val name: String
)