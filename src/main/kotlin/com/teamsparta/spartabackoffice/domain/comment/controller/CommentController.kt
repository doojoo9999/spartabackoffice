package com.teamsparta.spartabackoffice.domain.comment.controller

import com.teamsparta.spartabackoffice.domain.comment.dto.reponse.CommentResponse
import com.teamsparta.spartabackoffice.domain.comment.dto.request.CreateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.request.UpdateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RequestMapping("/comments/{memberId}/{postId}")
@RestController
class CommentController(
    private val commentService: CommentService
) {

    //댓글 작성
    @PostMapping
    fun createComment(
        @RequestParam postId: Long,
        @RequestBody createCommentRequest: CreateCommentRequest,
        principal: Principal
    ): ResponseEntity<CommentResponse>{
        val result = commentService.createComment(postId, createCommentRequest)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(result)
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    fun updateComment(@PathVariable commentId: Long,
                      @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse>{

        val updateComment = commentService.updateComment(commentId, updateCommentRequest)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(updateComment)
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ) : ResponseEntity<CommentResponse> {
        return  ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(postId, commentId, updateCommentRequest, userPrincipal))


    }
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ) : ResponseEntity<Unit> {

        commentService.deleteComment(postId, commentId, userPrincipal)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
    @GetMapping( )
    fun getCommentList() : ResponseEntity<List<CommentResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getCommentList())

    }

    fun deleteComment(@PathVariable commentId: Long
    ): ResponseEntity<String>{
       commentService.deleteComment(commentId)

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}