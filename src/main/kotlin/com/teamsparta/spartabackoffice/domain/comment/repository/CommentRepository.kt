package com.teamsparta.spartabackoffice.domain.comment.repository

import com.teamsparta.spartabackoffice.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
}