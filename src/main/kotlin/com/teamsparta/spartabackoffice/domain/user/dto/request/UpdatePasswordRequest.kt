package com.teamsparta.spartabackoffice.domain.user.dto.request

data class UpdatePasswordRequest (

    val oldPassword : String,
    val newPassword : String,
    val confirmPassword : String,

)