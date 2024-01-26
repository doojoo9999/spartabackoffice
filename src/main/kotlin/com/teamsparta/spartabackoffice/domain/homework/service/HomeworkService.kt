package com.teamsparta.spartabackoffice.domain.homework.service

import com.teamsparta.spartabackoffice.domain.homework.dto.request.SubmitRequest
import com.teamsparta.spartabackoffice.domain.homework.dto.response.SubmitResponse
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile

interface HomeworkService {

    fun submitHomework(file: MultipartFile, request: SubmitRequest, userPrincipal : UserPrincipal) : SubmitResponse

}