package com.teamsparta.spartabackoffice.domain.comment.service

import com.teamsparta.spartabackoffice.domain.comment.dto.request.CreateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.request.UpdateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.response.CommentResponse
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal

interface CommentService {

    fun createComment(postId : Long, request: CreateCommentRequest, userPrincipal : UserPrincipal) : CommentResponse

    fun updateComment(postId : Long, commentId : Long, request: UpdateCommentRequest, userPrincipal: UserPrincipal) : CommentResponse

    fun deleteComment(postId : Long, commentId : Long, userPrincipal: UserPrincipal)

    fun getCommentList() : List<CommentResponse>
}