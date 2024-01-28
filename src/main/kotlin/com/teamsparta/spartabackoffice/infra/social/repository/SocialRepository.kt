package com.teamsparta.spartabackoffice.infra.social.repository

import com.teamsparta.spartabackoffice.infra.social.model.SocialEntity
import org.springframework.data.jpa.repository.JpaRepository


interface SocialRepository : JpaRepository<SocialEntity, Long> {

    fun findByEmail(email: String) : SocialEntity?
    fun existsByEmail(email: String) : Boolean
}