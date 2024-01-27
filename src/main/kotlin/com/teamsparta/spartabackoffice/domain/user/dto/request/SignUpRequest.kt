package com.teamsparta.spartabackoffice.domain.user.dto.request

import com.teamsparta.spartabackoffice.domain.user.model.UserRole
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignUpRequest (
    var email: String,
    var password: String,
    var name: String,
    var role: UserRole
)