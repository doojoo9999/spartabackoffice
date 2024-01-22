package com.teamsparta.spartabackoffice.domain.post.service

import com.teamsparta.spartabackoffice.domain.post.dto.request.CreatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.DeletePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.UpdatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import com.teamsparta.spartabackoffice.domain.post.model.Complete
import com.teamsparta.spartabackoffice.domain.post.model.PostEntity
import com.teamsparta.spartabackoffice.domain.post.model.toResponse
import com.teamsparta.spartabackoffice.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull

class PostServiceImpl (
    val postRepository: PostRepository
)
    : PostService {
    override fun createPost(request: CreatePostRequest): PostResponse {
        val user = postRepository.findByIdOrNull(request.userId) ?: throw IllegalStateException ("User Not Found")
        return postRepository.save(
            PostEntity (
                title = request.title,
                content = request.content,
                complete = Complete.NOT_COMPLETE,
//                user = user,
                private = request.private,
            )
        ).toResponse()
    }

    override fun getPostList(): List<PostResponse> {
        return postRepository.findAll().map {it.toResponse()}
    }

    override fun updatePost(request: UpdatePostRequest): PostResponse {
        val user = postRepository.findByIdOrNull(request.userId) ?: throw IllegalStateException ("User Not Found")
        val post = postRepository.findByIdOrNull(request.postId) ?: throw IllegalStateException ("Post Not Found")

        val (userId, id, title, content, private) = request

//        post.userId = userId
        post.id = id
        post.title = title
        post.content = content
        post.private = private

        return postRepository.save(post).toResponse()
    }

    override fun deletePost(request: DeletePostRequest) {
        val user = postRepository.findByIdOrNull(request.userId) ?: throw IllegalStateException ("User Not Found")
        val post = postRepository.findByIdOrNull(request.postId) ?: throw IllegalStateException ("Post Not Found")

        postRepository.delete(post)
    }
}