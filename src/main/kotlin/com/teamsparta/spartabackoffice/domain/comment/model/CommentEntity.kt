package com.teamsparta.spartabackoffice.domain.comment.model

import com.teamsparta.spartabackoffice.domain.comment.dto.response.CommentResponse
import com.teamsparta.spartabackoffice.domain.post.model.PostEntity
import com.teamsparta.spartabackoffice.domain.user.model.UserEntity
import jakarta.persistence.*

@Entity
class CommentEntity(

    @Column(name = "content")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: PostEntity,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user : UserEntity
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}



fun CommentEntity.toResponse() : CommentResponse {
    return CommentResponse (
        userId = user.id,
        postId = post.id,
        content = content
    )
}