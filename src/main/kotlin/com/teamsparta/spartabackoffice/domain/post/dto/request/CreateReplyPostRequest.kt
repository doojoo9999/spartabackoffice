package com.teamsparta.spartabackoffice.domain.post.dto.request

data class CreateReplyPostRequest(
    val userId: Long,
    val parentPostId: Long,
    val title: String,
    val content: String,
    val private: Boolean,
)
