package com.teamsparta.spartabackoffice.domain.backoffice.controller

import com.teamsparta.spartabackoffice.domain.backoffice.dto.response.NotCompletedPostResponse
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/backoffice")
class BackOfficeController {

    @GetMapping
    fun getNotCompletedPostList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ) : ResponseEntity<List<NotCompletedPostResponse>> {

    }

    }

}