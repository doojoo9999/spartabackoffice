package com.teamsparta.spartabackoffice.infra.security

data class TokenInfo(
    val grantType : String,
    val accessToken : String
)