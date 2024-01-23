package com.teamsparta.spartabackoffice.domain.backoffice.dto.response

data class NotCompletedPostResponse(
    val userId : Long,
    val postId : Long,
    val complete: String,
)
