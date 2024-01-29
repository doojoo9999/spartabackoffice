package com.teamsparta.spartabackoffice.domain.user.dto.request

import com.teamsparta.spartabackoffice.domain.user.model.UserRole

data class ChangeRoleRequest(
    val newRole : UserRole
)
