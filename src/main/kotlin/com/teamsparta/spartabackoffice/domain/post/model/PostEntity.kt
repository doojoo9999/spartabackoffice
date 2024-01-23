package com.teamsparta.spartabackoffice.domain.post.model

import com.teamsparta.spartabackoffice.domain.post.dto.response.PostResponse
import jakarta.persistence.*
import org.springframework.data.annotation.Id

@Entity
@Table (name = "posts")
class PostEntity (
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column (name = "title", nullable = false )
    var title: String,

    @Column (name = "content", nullable = false)
    var content: String,

    @Column (name = "private", nullable = false)
    var private : Boolean,

    @Column (name = "complete", nullable = false)
    @Enumerated(EnumType.STRING)
    var complete : Complete,

    @Column(name = "parentpostid", nullable = true)
    var parentPostId: Long? = null

//    TODO("UserEntity 추가 후 수정 필요")
//    @JoinColumn (name = "userId")
//    @ManyToOne
//    var user : UserEntity

){

}

fun PostEntity.toResponse() : PostResponse {
    return PostResponse(
        userId = /*user.*/id,
        postId = id,
        title = title,
        content = content,
        private = private,
        complete = complete,
        parentCommentId = null
    )
}