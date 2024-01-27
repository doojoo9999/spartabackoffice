package com.teamsparta.spartabackoffice.domain.comment.service

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