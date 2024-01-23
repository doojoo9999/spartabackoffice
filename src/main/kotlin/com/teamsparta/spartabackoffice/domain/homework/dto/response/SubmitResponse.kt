package com.teamsparta.spartabackoffice.domain.homework.dto.response

import com.teamsparta.spartabackoffice.domain.exception.dto.ErrorResponse

data class SubmitResponse(
    val uploadedUrl: String?, // 파일업로드 되면 url 을 준다 (프론트에서 쓸 수도 낼 튜터님한테 물어보고 필요없으면 주석)
    val message: String?,
)