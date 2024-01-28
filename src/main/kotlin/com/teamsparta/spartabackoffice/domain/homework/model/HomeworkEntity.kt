package com.teamsparta.spartabackoffice.domain.homework.model

import com.teamsparta.spartabackoffice.domain.post.model.Complete
import com.teamsparta.spartabackoffice.domain.user.model.UserEntity
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.storage.Storage
import jakarta.persistence.*

@Entity
@Table(name = "homeworks")
class HomeworkEntity(
    @JoinColumn (name = "userId", nullable = false)
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val user: UserEntity,

    @Column (name = "title", nullable = false)
    val title : String?,

    @Column (name = "content", nullable = true)
    val content : String?,

    @Column (name = "complete", nullable = false)
    @Enumerated(EnumType.STRING)
    val complete : Complete?,

    @Column (name = "grade", nullable = true)
    val grade : String?,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0
}


