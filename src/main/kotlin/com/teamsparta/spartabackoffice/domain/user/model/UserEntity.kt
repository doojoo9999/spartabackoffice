package com.teamsparta.spartabackoffice.domain.user.model

import com.teamsparta.spartabackoffice.domain.user.dto.request.ChangeRoleRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserRoleResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import jakarta.persistence.*

@Entity(name="users")
class UserEntity(

    email: String,
    password: String,
    name: String,

    @ElementCollection // 값타입 컬렉션 1:N 관계를 가짐 ( 엔티티의 기본 키를 PK겸 FK로 사용함 )
    @CollectionTable(name = "user_old_passwords", joinColumns = [JoinColumn(name="user_id")])
    @Column(name = "old_passwords")
    val oldPasswords: MutableList<String> = mutableListOf(),

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    var role: UserRole,

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    val platform: Platform?,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var email = email

    @Column(nullable = false)
    var password = password

    @Column(nullable = false)
    var name = name

}

fun UserEntity.toResponse() : UserResponse {
    return UserResponse(
        id = id,
        email = email,
        name = name,
        role = role,
        platform = platform
    )
}

