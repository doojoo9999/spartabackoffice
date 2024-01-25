package com.teamsparta.spartabackoffice.domain.user.service

import com.teamsparta.spartabackoffice.domain.user.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse

interface UserService {
    fun signUp(request: SignUpRequest): UserResponse
    fun login(request: LoginRequest): Pair<UserResponse, String>
    fun getUser(userId: Long): UserResponse
    fun deleteUser(userId: Long)
}