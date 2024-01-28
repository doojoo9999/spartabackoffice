package com.teamsparta.spartabackoffice.domain.post.model

import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import com.teamsparta.spartabackoffice.domain.user.model.UserEntity
import jakarta.persistence.*

@Entity
@Table (name = "posts")
class PostEntity (


    @Column (name = "title", nullable = false )
    var title: String,

    @Column (name = "content", nullable = false)
    var content: String,

    @Column (name = "private", nullable = false)
    var private : Boolean,

    @Column (name = "complete", nullable = false)
    @Enumerated(EnumType.STRING)
    var complete : Complete,

    @JoinColumn (name = "userId")
    @ManyToOne (cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var user : UserEntity

){

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    var id: Long = 0

}

fun PostEntity.toResponse() : PostResponse {
    return PostResponse(
        userId = user.id,
        postId = id,
        title = title,
        content = content,
        private = private,
        complete = complete
    )
}