package com.teamsparta.spartabackoffice.domain.post.dto.request

import com.teamsparta.spartabackoffice.domain.post.model.Complete

data class UpdatePostRequest (
    val postId : Long,
    val title : String,
    val content : String,
    val private : Boolean,
    val complete : Complete
)