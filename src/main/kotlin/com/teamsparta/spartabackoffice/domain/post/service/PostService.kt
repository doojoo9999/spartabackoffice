package com.teamsparta.spartabackoffice.domain.post.service

import com.teamsparta.spartabackoffice.domain.post.dto.request.CreatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.CreateReplyPostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.DeletePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.UpdatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.stereotype.Service

@Service
interface PostService {

    fun getPostList(): List<PostResponse>

    fun createPost(request: CreatePostRequest, userPrincipal : UserPrincipal): PostResponse

    fun updatePost(request: UpdatePostRequest, userPrincipal : UserPrincipal): PostResponse

    fun deletePost(request: DeletePostRequest, userPrincipal : UserPrincipal)

}
