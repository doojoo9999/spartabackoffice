package com.teamsparta.spartabackoffice.domain.backoffice.repository

import com.teamsparta.spartabackoffice.domain.comment.model.QCommentEntity
import com.teamsparta.spartabackoffice.domain.homework.model.QHomeworkEntity
import com.teamsparta.spartabackoffice.domain.post.model.Complete
import com.teamsparta.spartabackoffice.domain.post.model.PostEntity
import com.teamsparta.spartabackoffice.domain.post.model.QPostEntity
import com.teamsparta.spartabackoffice.infra.querydsl.QueryDslSupport

class BackOfficeRepositoryImpl : QueryDslSupport(), CustomBackOfficeRepository {

    private val post = QPostEntity.postEntity
    override fun getNotCompletedPostList(): List<PostEntity> {
        return queryFactory.selectFrom(post)
            .where (post.complete.notIn(Complete.COMPLETE))
            .fetch()
    }

}