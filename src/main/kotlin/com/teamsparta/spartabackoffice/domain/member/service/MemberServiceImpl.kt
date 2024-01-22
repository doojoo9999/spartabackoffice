package com.teamsparta.spartabackoffice.domain.member.service

import com.teamsparta.spartabackoffice.domain.member.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.member.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.member.dto.response.MemberResponse
import com.teamsparta.spartabackoffice.domain.member.model.MemberEntity
import com.teamsparta.spartabackoffice.domain.member.model.toResponse
import com.teamsparta.spartabackoffice.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(private val memberRepository: MemberRepository) : MemberService {
    override fun signUp(request: SignUpRequest): MemberResponse {
        val member = MemberEntity(
            email = request.email,
            password = request.password, // 실제 서비스에서는 비밀번호를 암호화하여 저장
            name = request.name,
            role = request.role
        )
        memberRepository.save(member)
        return member.toResponse()
    }

    override fun login(request: LoginRequest): String {
        val member = memberRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("Invalid email or password")
        if (member.password != request.password) { // 실제 서비스에서는 입력된 비밀번호를 암호화하여 저장된 비밀번호와 비교
            throw IllegalArgumentException("Invalid email or password")
        }
        return "Login successful" // 실제 서비스에서는 JWT 토큰 등을 반환
    }

}