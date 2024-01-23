package com.teamsparta.spartabackoffice.domain.post.dto.request

data class UpdatePostRequest (
    val userId : Long,
    val postId : Long,
    val title : String,
    val content : String,
    val private : Boolean,
)