package com.teamsparta.spartabackoffice.domain.homework.service

import com.teamsparta.spartabackoffice.domain.homework.dto.request.SubmitRequest
import com.teamsparta.spartabackoffice.domain.homework.dto.response.SubmitResponse
import com.teamsparta.spartabackoffice.infra.SupabaseConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*


@Service
class HomeworkServiceImpl(
    val supabaseUrl : SupabaseConfig,
    val supabase : SupabaseClient
    ): HomeworkService {

    override suspend fun submitHomework(file: MultipartFile): SubmitResponse {
        val bucketName = "homework"
        val filename = UUID.randomUUID().toString() + "-" + (file.originalFilename ?: "homework")
        val uploadedUrl = "${supabaseUrl}/storage/v1/object/public/$filename"

        supabase.storage.from(bucketName).upload(filename, file.bytes)

        return SubmitResponse(
            uploadedUrl = uploadedUrl,
            message = "업로드 완료."
        )
    }

}