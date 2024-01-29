package com.teamsparta.spartabackoffice.domain.user.dto.response

import com.teamsparta.spartabackoffice.domain.user.model.UserRole

data class UpdateUserRoleResponse(
    val newRole : UserRole
)
