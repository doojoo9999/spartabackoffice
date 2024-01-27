package com.teamsparta.spartabackoffice.domain.backoffice.service

import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.GetCommentResponse
import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.GetHomeworkResponse
import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.GetPostResponse
import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.NotCompletedPostResponse
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.stereotype.Service

@Service
class BackOfficeServiceImpl : BackOfficeService {
    override fun getNotCompletedPostList(userPrincipal: UserPrincipal): List<NotCompletedPostResponse> {
        TODO("Not yet implemented")
    }

}