package com.teamsparta.spartabackoffice.domain.homework.dto.request

import org.springframework.web.multipart.MultipartFile

data class SubmitRequest(
    val userId: String,
    val file: MultipartFile
)