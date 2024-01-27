package com.teamsparta.spartabackoffice.domain.user.dto.response

data class UserResponse(

    //val id: Long?,
    var id: Long?,
    var email: String,
    var name: String?,
    var role: String
)