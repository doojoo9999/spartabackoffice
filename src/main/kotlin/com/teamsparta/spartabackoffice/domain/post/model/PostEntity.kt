package com.teamsparta.spartabackoffice.domain.post.model

import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import jakarta.persistence.*
import org.springframework.data.annotation.Id

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

//    TODO("UserEntity 추가 후 수정 필요")
//    @JoinColumn (name = "userId")
//    @ManyToOne
//    var user : UserEntity

){
    @jakarta.persistence.Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    var id: Long = 0
}

fun PostEntity.toResponse() : PostResponse {
    return PostResponse(
        postId = id,
        title = title,
        content = content,
        private = private,
        complete = complete
    )
}