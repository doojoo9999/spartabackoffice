package com.teamsparta.spartabackoffice.domain.comment.dto

import com.teamsparta.spartabackoffice.domain.comment.model.Comment

class CommentResponse(
    var userId: String,
    var postId: Long,
    var content: String
) {

    companion object{
        fun toCommentResponse(comment: Comment): CommentResponse{
            return CommentResponse(
                userId = comment.user,
                content = comment.content,
                postId = comment.post
            )
        }
    }
}