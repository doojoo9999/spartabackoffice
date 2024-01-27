package com.teamsparta.spartabackoffice.domain.comment.dto.response

data class CommentResponse(
    val userId: Long?,
    val postId: Long,
    val content: String
)
