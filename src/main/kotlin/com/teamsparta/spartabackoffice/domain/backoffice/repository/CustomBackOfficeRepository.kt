package com.teamsparta.spartabackoffice.domain.backoffice.repository

import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.NotCompletedPostResponse
import com.teamsparta.spartabackoffice.domain.comment.model.CommentEntity
import com.teamsparta.spartabackoffice.domain.homework.model.HomeworkEntity
import com.teamsparta.spartabackoffice.domain.post.model.PostEntity
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal

interface CustomBackOfficeRepository {

    fun getNotCompletedPostList(): List<PostEntity>

}
