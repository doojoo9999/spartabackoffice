package com.teamsparta.spartabackoffice.domain.post.dto.response

import com.teamsparta.spartabackoffice.domain.post.model.Complete

data class PostResponse (
    val userId: Long,
    val postId: Long,
    val title: String,
    val content : String,
    val private : Boolean,
    val complete : Complete
)