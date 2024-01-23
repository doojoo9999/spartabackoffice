package com.teamsparta.spartabackoffice.domain.post.dto.request

data class CreatePostRequest (
    val title: String,
    val content: String,
    val private: Boolean,
)