package com.teamsparta.spartabackoffice.domain.post.repository

import com.teamsparta.spartabackoffice.domain.post.model.Complete
import com.teamsparta.spartabackoffice.domain.post.model.PostEntity
import com.teamsparta.spartabackoffice.domain.post.model.QPostEntity
import com.teamsparta.spartabackoffice.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl : QueryDslSupport(), CustomPostRepository {

    private val post = QPostEntity.postEntity
    override fun getNotCompletedPostList(): List<PostEntity> {
        return queryFactory.selectFrom(post)
            .where (post.complete.notIn(Complete.COMPLETE))
            .fetch()
    }

}