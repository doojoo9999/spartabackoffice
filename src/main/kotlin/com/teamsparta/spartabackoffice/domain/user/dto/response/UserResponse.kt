package com.teamsparta.spartabackoffice.domain.user.dto.response

data class UserResponse(
    //val id: Long,
    val id: Long?,
    val email: String,
    val name: String,
    val role: String
)