package com.teamsparta.spartabackoffice.domain.member.service

import com.teamsparta.spartabackoffice.domain.member.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.member.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.member.dto.response.MemberResponse

interface MemberService {
    fun signUp(request: SignUpRequest): MemberResponse
    fun login(request: LoginRequest): String

    //fun logout(token: String): String
}