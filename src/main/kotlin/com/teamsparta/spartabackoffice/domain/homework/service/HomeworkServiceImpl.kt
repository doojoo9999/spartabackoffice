package com.teamsparta.spartabackoffice.domain.homework.service

import com.teamsparta.spartabackoffice.domain.exception.ModelNotFoundException
import com.teamsparta.spartabackoffice.domain.homework.dto.request.SubmitRequest
import com.teamsparta.spartabackoffice.domain.homework.dto.response.SubmitResponse
import com.teamsparta.spartabackoffice.domain.homework.model.HomeworkEntity
import com.teamsparta.spartabackoffice.domain.homework.repository.HomeworkRepository
import com.teamsparta.spartabackoffice.domain.post.model.Complete
import com.teamsparta.spartabackoffice.domain.user.repository.UserRepository
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.runBlocking
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.security.Principal
import java.util.*


@Service
class HomeworkServiceImpl(
//    val supabaseUrl : SupabaseConfig,
    val supabase : SupabaseClient,
    val homeworkRepository : HomeworkRepository,
    val userRepository: UserRepository,
//    val userAuthentication : UserAuthentication
    ): HomeworkService {


    override fun submitHomework(file: MultipartFile, request: SubmitRequest, userPrincipal : UserPrincipal): SubmitResponse {

        val BUCKET_NAME = "homework"
        val filePath = UUID.randomUUID().toString() + '.' + (file.originalFilename!!.split('.')[1])



        runBlocking {
            supabase.storage.from(BUCKET_NAME).upload(filePath, file.bytes, upsert = false)
        }

        val uploadedUrl = supabase.storage.from("$BUCKET_NAME").publicUrl(filePath)

//        val userauthentication = SecurityContextHolder.getContext().authentication
//        val userPrincipal = userauthentication.principal as UserPrincipal
//        val userId = userPrincipal.id
//        val userId = userAuthentication.getUserId()

        val userId = userPrincipal.id

        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("user", userId)

        val homework = HomeworkEntity(
            user = user,
            complete = Complete.NOT_COMPLETE,
            title = request.title,
            content = uploadedUrl,
            grade = null
        )

        homeworkRepository.save(homework)

        return SubmitResponse(
            uploadedUrl = uploadedUrl,
            message = "업로드 완료."
        )
    }

}