package com.teamsparta.spartabackoffice.domain.comment.model

@Entity
@Table (name = "comments")
class CommentEntity(

    @JoinColumn (name = "userId", nullable = false)
    @ManyToOne
    var user : UserEntity,

    @JoinColumn (name = "postId")
    @ManyToOne
    var post : PostEntity,


    @Column (name = "content")
    var content : String,

) {

    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    var id : Long = 0

}