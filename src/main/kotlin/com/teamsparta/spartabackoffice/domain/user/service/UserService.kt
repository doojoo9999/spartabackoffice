package com.teamsparta.spartabackoffice.domain.user.service

import com.teamsparta.spartabackoffice.domain.user.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdatePasswordRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdateUserRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import com.teamsparta.spartabackoffice.infra.social.dto.SocialResponse

interface UserService {
    fun signUp(request: SignUpRequest): UserResponse
    fun login(request: LoginRequest): Pair<UserResponse, String>
    fun getUser(userId: Long, userPrincipal: UserPrincipal): Any
    fun updateUser(userId: Long, request: UpdateUserRequest) : UpdateUserResponse
    fun deleteUser(userId: Long)

    //fun logout(token: String): String


    fun updatePassword (userPrincipal: UserPrincipal, request : UpdatePasswordRequest ) : Any

}