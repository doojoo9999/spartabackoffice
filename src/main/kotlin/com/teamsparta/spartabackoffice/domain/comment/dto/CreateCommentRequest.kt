package com.teamsparta.spartabackoffice.domain.comment.dto

class CreateCommentRequest(
    var userId: Long,
    var postId: Long,
    var content: String
) {
}