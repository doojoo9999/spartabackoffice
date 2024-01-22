package com.teamsparta.spartabackoffice.domain.comment.service

import com.teamsparta.spartabackoffice.domain.comment.dto.CommentResponse
import com.teamsparta.spartabackoffice.domain.comment.dto.CreateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.UpdateCommentRequest

interface CommentService {

    fun createComment(postId: Long, createCommentRequest: CreateCommentRequest ): CommentResponse

    fun updateComment(commentId: Long, request: UpdateCommentRequest): CommentResponse

    fun deleteComment(commentId: Long)
}