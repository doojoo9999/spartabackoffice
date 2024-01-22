package com.teamsparta.spartabackoffice.domain.post.controller

import com.teamsparta.spartabackoffice.domain.post.dto.request.CreatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.request.UpdatePostRequest
import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import com.teamsparta.spartabackoffice.domain.post.repository.PostRepository
import com.teamsparta.spartabackoffice.domain.post.service.PostService
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/post")
@RestController
class PostController(
    private val postService: PostService,
    private val postRepository: PostRepository
) {

    @PostMapping()
    fun createPost(
        @RequestBody createPostRequest: CreatePostRequest
    ) : ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(createPostRequest))
    }

    @GetMapping()
    fun getPostList() : ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostList())
    }

    @PutMapping("/{postId}")
    fun updatePostList(
        @PathVariable postId: String,
        @RequestBody updatePostRequest: UpdatePostRequest
    ):ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(updatePostRequest))
    }
    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId:String
        ) : ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }



}