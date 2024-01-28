package com.teamsparta.spartabackoffice.domain.post.dto.response

import com.teamsparta.spartabackoffice.domain.post.model.Complete

data class NotCompletedPostResponse(
    val userId : Long?,
    val postId : Long,
    val title : String,
    val complete: Complete,
)
