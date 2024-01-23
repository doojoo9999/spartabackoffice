package com.teamsparta.spartabackoffice.domain.comment.dto.request

class CreateCommentRequest(
    var postId: Long,
    var content: String
) {
}