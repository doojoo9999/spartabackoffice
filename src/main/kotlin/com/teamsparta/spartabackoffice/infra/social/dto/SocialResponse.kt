package com.teamsparta.spartabackoffice.infra.social.dto

import com.teamsparta.spartabackoffice.domain.user.model.UserRole

data class SocialResponse(

    var id: Long?,
    var email: String,
    var role: UserRole?
)