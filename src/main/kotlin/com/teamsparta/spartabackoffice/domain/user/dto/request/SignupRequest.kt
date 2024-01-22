package com.teamsparta.spartabackoffice.domain.user.dto.request

data class SignUpRequest (
    var email: String,
    var password: String,
    var name: String
)