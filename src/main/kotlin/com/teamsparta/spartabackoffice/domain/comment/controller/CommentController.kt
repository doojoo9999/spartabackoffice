package com.teamsparta.spartabackoffice.domain.comment.controller

import com.teamsparta.spartabackoffice.domain.comment.dto.reponse.CommentResponse
import com.teamsparta.spartabackoffice.domain.comment.dto.request.CreateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.dto.request.UpdateCommentRequest
import com.teamsparta.spartabackoffice.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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

    //댓글 삭제
    @DeleteMapping("/{commentId}")

    fun deleteComment(@PathVariable commentId: Long
    ): ResponseEntity<String>{
       commentService.deleteComment(commentId)

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}