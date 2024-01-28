package com.teamsparta.spartabackoffice.domain.exception

class EmailNotFoundException(
    email: String
) : RuntimeException("해당 이메일($email)을 가진 유저를 찾을 수 없습니다.")