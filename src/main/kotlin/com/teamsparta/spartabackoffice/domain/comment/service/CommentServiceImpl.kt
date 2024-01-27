package com.teamsparta.spartabackoffice.domain.comment.service

import com.teamsparta.spartabackoffice.domain.comment.dto.request.CreateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.request.UpdateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.response.CommentResponse
import com.teamsparta.spartabackoffice.domain.comment.model.CommentEntity
import com.teamsparta.spartabackoffice.domain.comment.model.toResponse
import com.teamsparta.spartabackoffice.domain.comment.repository.CommentRepository
import com.teamsparta.spartabackoffice.domain.post.repository.PostRepository
import com.teamsparta.spartabackoffice.domain.user.repository.UserRepository
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class CommentServiceImpl(
    val userRepository : UserRepository,
    val postRepository : PostRepository,
    val commentRepository : CommentRepository
) : CommentService {

    override fun createComment(postId : Long, request: CreateCommentRequest, userPrincipal : UserPrincipal) : CommentResponse {

        val userId = userPrincipal.id

        val user = userRepository.findByIdOrNull(userId) ?: throw IllegalStateException ("User Not Found")
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalStateException ("Post Not Found")

        return commentRepository.save(
            CommentEntity (
                user = user,
                post = post,
                content = request.content
            )
        ).toResponse()

    }

    override fun updateComment(postId : Long, commentId : Long, request: UpdateCommentRequest, userPrincipal: UserPrincipal) : CommentResponse {

        val userId = userPrincipal.id

        val user = userRepository.findByIdOrNull(userId) ?: throw IllegalStateException ("User Not Found")
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalStateException ("Post Not Found")
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw IllegalStateException ("Post Not Found")

        val (content) = request

        comment.content = content

        return commentRepository.save(comment).toResponse()
    }

    override fun deleteComment(postId : Long, commentId : Long, userPrincipal: UserPrincipal) {

        val userId = userPrincipal.id

        val user = userRepository.findByIdOrNull(userId) ?: throw IllegalStateException ("User Not Found")
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalStateException ("Post Not Found")
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw IllegalStateException ("Post Not Found")

        commentRepository.delete(comment)

    }

    override fun getCommentList() : List<CommentResponse> {
        return commentRepository.findAll().map { it.toResponse() }
    }

}