package com.teamsparta.spartabackoffice.domain.post.service

import com.teamsparta.spartabackoffice.domain.post.dto.request.CreatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.DeletePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.UpdatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import org.springframework.stereotype.Service

@Service
interface PostService {

    fun getPostList () : List<PostResponse>
    fun createPost (request: CreatePostRequest) : PostResponse

    fun updatePost (request: UpdatePostRequest) : PostResponse

    fun deletePost (request: DeletePostRequest)
}