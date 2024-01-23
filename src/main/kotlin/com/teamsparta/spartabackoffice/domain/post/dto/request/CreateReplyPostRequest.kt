package com.teamsparta.spartabackoffice.domain.post.dto.request

data class CreateReplyPostRequest(
    val title: String,
    val content: String,
    val private: Boolean,
)
