package com.teamsparta.spartabackoffice.domain.homework.repository

import com.teamsparta.spartabackoffice.domain.homework.dto.request.SubmitRequest
import com.teamsparta.spartabackoffice.domain.homework.model.HomeworkEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.context.SecurityContextHolder

interface HomeworkRepository : JpaRepository <HomeworkEntity, Long> {



}