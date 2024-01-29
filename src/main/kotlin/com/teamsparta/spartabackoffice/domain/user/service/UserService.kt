package com.teamsparta.spartabackoffice.domain.user.service

import com.teamsparta.spartabackoffice.domain.user.dto.request.*
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserRoleResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal

interface UserService {
    fun signUp(request: SignUpRequest): UserResponse
    fun login(request: LoginRequest): Pair<UserResponse, String>
    fun getUser(id: Long, platform: String): Any
    fun updateUser(userId: Long, request: UpdateUserRequest) : UpdateUserResponse
    fun deleteUser(id: Long, platform: String): Any

    //fun logout(token: String): String


    fun updatePassword (userPrincipal: UserPrincipal, request : UpdatePasswordRequest ) : Any


    fun changeUserRole (userPrincipal: UserPrincipal, userId: Long, request: ChangeRoleRequest) : UserResponse
}