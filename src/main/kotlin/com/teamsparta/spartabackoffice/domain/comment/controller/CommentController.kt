package com.teamsparta.spartabackoffice.domain.comment.controller

import com.teamsparta.spartabackoffice.domain.comment.dto.request.CreateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.request.UpdateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.response.CommentResponse
import com.teamsparta.spartabackoffice.domain.comment.service.CommentService
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/post/{postId}/comment")
@RestController
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping()
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody createCommentRequest: CreateCommentRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, createCommentRequest, userPrincipal))
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(postId, commentId, updateCommentRequest, userPrincipal))


    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {

        commentService.deleteComment(postId, commentId, userPrincipal)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @GetMapping()
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun getCommentList(): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getCommentList())

    }


}