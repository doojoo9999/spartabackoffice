package com.teamsparta.spartabackoffice.domain.homework.service

import com.teamsparta.spartabackoffice.domain.homework.dto.request.SubmitRequest
import com.teamsparta.spartabackoffice.domain.homework.dto.response.SubmitResponse
import org.springframework.web.multipart.MultipartFile

interface HomeworkService {

    suspend fun submitHomework(file: MultipartFile) : SubmitResponse

}