package com.teamsparta.spartabackoffice.domain.post.service

import com.teamsparta.spartabackoffice.domain.post.dto.request.CreatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.UpdatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.response.NotCompletedPostResponse
import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import com.teamsparta.spartabackoffice.domain.post.model.Complete
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.stereotype.Service

interface PostService {

    fun getPostList(): List<PostResponse>

    fun createPost(request: CreatePostRequest, userPrincipal : UserPrincipal): PostResponse

    fun updatePost(request: UpdatePostRequest, complete: Complete, userPrincipal : UserPrincipal): PostResponse

    fun deletePost(postId : Long, userPrincipal : UserPrincipal)

    fun getNotCompletedPostList(userPrincipal: UserPrincipal): List<NotCompletedPostResponse>
}
