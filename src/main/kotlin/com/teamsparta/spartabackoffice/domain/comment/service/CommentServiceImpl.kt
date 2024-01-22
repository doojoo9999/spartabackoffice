package com.teamsparta.spartabackoffice.domain.comment.service

import com.teamsparta.spartabackoffice.domain.comment.dto.reponse.CommentResponse
import com.teamsparta.spartabackoffice.domain.comment.dto.request.CreateCommentRequest

import com.teamsparta.spartabackoffice.domain.comment.dto.request.UpdateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.repository.CommentRepository
import com.teamsparta.spartabackoffice.domain.exception.ModelNotFoundException
import com.teamsparta.spartabackoffice.domain.exception.dto.UnauthorizedAccess
import com.teamsparta.spartabackoffice.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.teamsparta.spartabackoffice.domain.comment.model.CommentEntity
import com.teamsparta.spartabackoffice.domain.comment.model.toResponse

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
): CommentService {
    @Transactional
    override fun createComment(postId: Long, createCommentRequest: CreateCommentRequest
    ): CommentResponse {
        val targetPost = postRepository.findByIdOrNull(postId)
            ?: throw Exception("target post is not found")

        val commentWriter = SecurityContextHolder.getContext().authentication.name

        return commentRepository.save(
            CommentEntity(
                content = createCommentRequest.content,
                post = targetPost,
//            user = commentWriter
            )
        ).toResponse()
    }

    @Transactional
    override fun updateComment(commentId: Long, request: UpdateCommentRequest
    ): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)
        val commentWriter = SecurityContextHolder.getContext().authentication.name
//        if(comment.user != commentWriter) throw UnauthorizedAccess()

        comment.content = request.content

        return commentRepository.save(comment).toResponse()
    }
    @Transactional
    override fun deleteComment(commentId: Long) {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)
        val commentWriter = SecurityContextHolder.getContext().authentication.name
//        if(comment.user != commentWriter) throw UnauthorizedAccess()

        commentRepository.delete(comment)

    }
}