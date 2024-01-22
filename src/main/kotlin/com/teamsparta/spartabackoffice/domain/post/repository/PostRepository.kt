package com.teamsparta.spartabackoffice.domain.post.repository

import com.teamsparta.spartabackoffice.domain.post.model.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository <PostEntity, Long> {
}