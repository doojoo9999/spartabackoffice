package com.teamsparta.spartabackoffice.infra.social.model

import com.teamsparta.spartabackoffice.domain.user.model.Platform
import com.teamsparta.spartabackoffice.domain.user.model.UserRole
import com.teamsparta.spartabackoffice.infra.social.dto.SocialResponse
import jakarta.persistence.*

@Entity(name="members")
class SocialEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "members_seq", allocationSize = 1)
    var id: Long = 0,

    var email: String,

    @Enumerated(EnumType.STRING)
    var role: UserRole,

    @Enumerated(EnumType.STRING)
    var platform: Platform

) {
    fun SocialEntity.toSocialResponse(): SocialResponse {
        return SocialResponse(
            id = id,
            email = email,
            role = role,
            platform = platform
        )
    }
}