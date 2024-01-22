package com.teamsparta.spartabackoffice.domain.member.repository

import com.teamsparta.spartabackoffice.domain.member.model.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<MemberEntity, Long> {
    fun findByEmail(email: String): MemberEntity?
}