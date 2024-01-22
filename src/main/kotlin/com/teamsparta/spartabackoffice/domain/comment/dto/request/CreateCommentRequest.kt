package com.teamsparta.spartabackoffice.domain.comment.dto.request

class CreateCommentRequest(
    var userId: Long,
    var postId: Long,
    var content: String
) {
}