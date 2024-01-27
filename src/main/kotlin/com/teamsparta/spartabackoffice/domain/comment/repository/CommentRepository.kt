package com.teamsparta.spartabackoffice.domain.comment.repository

import com.teamsparta.spartabackoffice.domain.comment.model.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository <CommentEntity, Long> {


}