package com.teamsparta.spartabackoffice.domain.post.service

import com.teamsparta.spartabackoffice.domain.exception.ModelNotFoundException
import com.teamsparta.spartabackoffice.domain.post.dto.request.CreatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.UpdatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.response.NotCompletedPostResponse
import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import com.teamsparta.spartabackoffice.domain.post.model.Complete
import com.teamsparta.spartabackoffice.domain.post.model.PostEntity
import com.teamsparta.spartabackoffice.domain.post.model.toResponse
import com.teamsparta.spartabackoffice.domain.post.repository.PostRepository
import com.teamsparta.spartabackoffice.domain.user.model.UserRole
import com.teamsparta.spartabackoffice.domain.user.repository.UserRepository
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.file.AccessDeniedException

@Service
class PostServiceImpl (
    val postRepository: PostRepository,
    val userRepository: UserRepository
)
    : PostService {
    override fun createPost(request: CreatePostRequest, userPrincipal : UserPrincipal): PostResponse {

        val userId = userPrincipal.id

        val user = userRepository.findByIdOrNull(userId) ?: throw IllegalStateException ("User Not Found")
        return postRepository.save(
            PostEntity (
                title = request.title,
                content = request.content,
                complete = Complete.NOT_COMPLETE,
                user = user,
                private = request.private,
            )
        ).toResponse()
    }

    override fun getPostList(): List<PostResponse> {
        return postRepository.findAll().map {it.toResponse()}
    }

    override fun updatePost(request: UpdatePostRequest, complete: Complete, userPrincipal : UserPrincipal): PostResponse {
        val userId = userPrincipal.id

        val user = userRepository.findByIdOrNull(userId) ?: throw IllegalStateException ("User Not Found")
        val post = postRepository.findByIdOrNull(request.postId) ?: throw IllegalStateException ("Post Not Found")

        val (id, title, content, private) = request

        post.user = user
        post.id = id
        post.title = title
        post.content = content
        post.private = private
        post.complete = complete

        return postRepository.save(post).toResponse()
    }

    @Transactional
    override fun deletePost(postId: Long, userPrincipal : UserPrincipal){

        val userId = userPrincipal.id

        val user = userRepository.findByIdOrNull(userId) ?: throw IllegalStateException ("User Not Found")
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalStateException ("Post Not Found")


        if (post.user.id == userId || user.role == UserRole.ADMIN) {
            postRepository.delete(post)
        } else {
            throw AccessDeniedException ("본인의 게시글만 삭제할 수 있습니다.")
        }

    }


    override fun getNotCompletedPostList(userPrincipal: UserPrincipal): List<NotCompletedPostResponse> {
        val userId = userPrincipal.id
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException ("User Id", userId)

        val notCompletedPosts = postRepository.getNotCompletedPostList()
        val response = notCompletedPosts.map { post ->
            NotCompletedPostResponse(post.user.id, post.id, post.title, post.complete)
        }

        return response

    }

}