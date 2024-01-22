package com.teamsparta.spartabackoffice.domain.comment.service

import com.teamsparta.spartabackoffice.domain.comment.dto.reponse.CommentResponse
import com.teamsparta.spartabackoffice.domain.comment.dto.request.CreateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.request.UpdateCommentRequest

interface CommentService {

    fun createComment(postId: Long, createCommentRequest: CreateCommentRequest): CommentResponse

    fun updateComment(commentId: Long, request: UpdateCommentRequest): CommentResponse

    fun deleteComment(commentId: Long)
}