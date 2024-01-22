package com.teamsparta.spartabackoffice.domain.member.dto.response

data class MemberResponse(
    val id: Long,
    val email: String,
    val name: String,
    val role: String
)