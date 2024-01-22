package com.teamsparta.spartabackoffice.domain.comment.service

import com.teamsparta.spartabackoffice.domain.comment.dto.CommentResponse
import com.teamsparta.spartabackoffice.domain.comment.dto.CreateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.repository.CommentRepository
import com.teamsparta.spartabackoffice.domain.exception.ModelNotFoundException
import com.teamsparta.spartabackoffice.domain.exception.dto.UnauthorizedAccess
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.xml.stream.events.Comment

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
): CommentService {
    @Transactional
    override fun createComment(postId: Long, createCommentRequest: CreateCommentRequest): CommentResponse {
        val targetPost = postRepository.findByIdOrNull(postId)
            ?: throw Exception("target post is not found")

        val commentWriter = SecurityContextHolder.getContext().authentication.name

        val comment = Comment(
            content = createCommentRequest.content,
            post = targetPost,
            user = commentWriter
        )

        val result = commentRepository.save(comment)

        return CommentResponse.toCommentResponse(result)
    }
    @Transactional
    override fun updateComment(commentId: Long, request: UpdateCommentRequest): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId)?: throw ModelNotFoundException("Comment", commentId)
        val commentWriter = SecurityContextHolder.getContext().authentication.name
        if(comment.user != commentWriter) throw UnauthorizedAccess()

        comment.content = request.content

        return CommentResponse.toCommentResponse(comment)
    }
    @Transactional
    override fun deleteComment(commentId: Long) {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        val commentWriter = SecurityContextHolder.getContext().authentication.name
        if(comment.user != commentWriter) throw UnauthorizedAccess()

        commentRepository.delete(comment)

    }
}