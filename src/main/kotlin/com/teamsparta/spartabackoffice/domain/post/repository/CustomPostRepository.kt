package com.teamsparta.spartabackoffice.domain.post.repository

import com.teamsparta.spartabackoffice.domain.post.model.PostEntity

interface CustomPostRepository {
    fun getNotCompletedPostList(): List<PostEntity>

}