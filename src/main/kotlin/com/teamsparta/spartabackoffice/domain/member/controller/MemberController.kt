package com.teamsparta.spartabackoffice.domain.member.controller

import com.teamsparta.spartabackoffice.domain.member.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.member.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.member.dto.response.MemberResponse
import com.teamsparta.spartabackoffice.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberController(private val memberService: MemberService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Void> {
        memberService.signUp(request)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        val message = memberService.login(request)
        return ResponseEntity.ok(message)
    }

}