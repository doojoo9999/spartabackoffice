package com.teamsparta.spartabackoffice.domain.homework.controller

import com.teamsparta.spartabackoffice.domain.homework.dto.request.SubmitRequest
import com.teamsparta.spartabackoffice.domain.homework.dto.response.SubmitResponse
import com.teamsparta.spartabackoffice.domain.homework.service.HomeworkService
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/homework")
class HomeworkController (
    private val homeworkService: HomeworkService
){

    @PostMapping("/upload")
    fun submitHomework(
        @RequestPart("file") file: MultipartFile,
        @RequestPart("submitRequest") submitRequest: SubmitRequest,
        @AuthenticationPrincipal userPrincipal : UserPrincipal
    ) : ResponseEntity <SubmitResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(homeworkService.submitHomework(file, submitRequest, userPrincipal))
    }

}