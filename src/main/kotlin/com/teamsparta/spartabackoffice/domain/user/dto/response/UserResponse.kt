package com.teamsparta.spartabackoffice.domain.user.dto.response

import com.teamsparta.spartabackoffice.domain.user.model.Platform
import com.teamsparta.spartabackoffice.domain.user.model.UserRole

data class UserResponse(

    //val id: Long?,
    var id: Long?,
    var email: String,
    var name: String,
    var role: UserRole?,
    var platform : Platform?
)