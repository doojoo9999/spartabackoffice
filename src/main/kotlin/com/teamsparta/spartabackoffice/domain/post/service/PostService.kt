package com.teamsparta.spartabackoffice.domain.post.service

import com.teamsparta.spartabackoffice.domain.post.dto.request.CreatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.CreateReplyPostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.DeletePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.UpdatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse


interface PostService {

    fun getPostList () : List<PostResponse>

    fun createPost (request: CreatePostRequest) : PostResponse

    fun updatePost (request: UpdatePostRequest) : PostResponse

    fun deletePost (request: DeletePostRequest)

    fun createReplyPost (postId:Long, parentPostId:Long, request: CreateReplyPostRequest) : PostResponse
}