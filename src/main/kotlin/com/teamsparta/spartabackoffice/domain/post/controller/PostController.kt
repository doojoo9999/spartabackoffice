package com.teamsparta.spartabackoffice.domain.post.controller

import com.teamsparta.spartabackoffice.domain.post.dto.request.CreatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.UpdatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.response.NotCompletedPostResponse
import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import com.teamsparta.spartabackoffice.domain.post.model.Complete
import com.teamsparta.spartabackoffice.domain.post.service.PostService
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/post")
@RestController
class PostController(
    private val postService: PostService,
) {

    @PostMapping()
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun createPost(
        @RequestBody createPostRequest: CreatePostRequest,
        @AuthenticationPrincipal userPrincipal : UserPrincipal
    ) : ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(createPostRequest, userPrincipal))
    }

    @GetMapping()
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun getPostList() : ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostList())
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun updatePostList(
        @PathVariable postId: Long,
        @RequestBody updatePostRequest: UpdatePostRequest,
        @RequestBody complete: Complete,
        @AuthenticationPrincipal userPrincipal : UserPrincipal
    ):ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(updatePostRequest, complete, userPrincipal))
    }
    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun deletePost(
        @PathVariable postId:Long,
        @AuthenticationPrincipal userPrincipal : UserPrincipal
        ) : ResponseEntity<Unit> {

        postService.deletePost(postId, userPrincipal)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @GetMapping("/completed")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR')")
    fun getNotCompletedPostList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ) : ResponseEntity<List<NotCompletedPostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getNotCompletedPostList(userPrincipal))
    }


}