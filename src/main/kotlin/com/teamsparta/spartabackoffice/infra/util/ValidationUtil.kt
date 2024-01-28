package com.teamsparta.spartabackoffice.infra.util

import java.util.regex.Pattern

object ValidationUtil {
    fun isValidUsername(username: String): Boolean {
        val regex = "^[가-힣]{2,8}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(username)
        return matcher.matches()
    }

    fun isValidPassword(password: String): Boolean {
        val regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}