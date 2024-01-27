package com.teamsparta.spartabackoffice.domain.backoffice.service

import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.GetCommentResponse
import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.GetHomeworkResponse
import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.GetPostResponse
import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.NotCompletedPostResponse
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal

interface BackOfficeService {

    fun getNotCompletedPostList(@AuthenticationPrincipal userPrincipal: UserPrincipal): List<NotCompletedPostResponse>
    fun getPostsByUserId(): List<GetPostResponse>

    fun getCommentsByUserId(): List<GetCommentResponse>
    fun getHomeworksByUserId(): List<GetHomeworkResponse>

    fun getHomeworkListByDate(): List<GetHomeworkResponse>
}