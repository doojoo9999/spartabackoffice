package com.teamsparta.spartabackoffice.domain.comment.model

import jakarta.persistence.*
import org.hibernate.annotations.Fetch

@Entity
class Comment(

    @Column(name = "content")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post,

    @Column(name = "user_id")
    var user: String
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}