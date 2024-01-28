package com.teamsparta.spartabackoffice.domain.user.controller

import com.teamsparta.spartabackoffice.domain.user.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdatePasswordRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdateUserRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.domain.user.service.UserService
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import com.teamsparta.spartabackoffice.infra.social.dto.SocialResponse
import com.teamsparta.spartabackoffice.infra.social.jwt.JwtDto
import com.teamsparta.spartabackoffice.infra.social.service.SocialService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService,
    private val socialService: SocialService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Void> {
        userService.signUp(request)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<UserResponse> {
        val (userResponse, token) = userService.login(request)
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .body(userResponse)
    }
    //소셜로그인
    @GetMapping("/login")
    fun socialLogin(@AuthenticationPrincipal oAuth2User: OAuth2User): ResponseEntity<JwtDto> {
        //이거 추가됨!!!
        return ResponseEntity.ok(socialService.socialLogin(oAuth2User))
    }

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: Long, @RequestParam platform: String): ResponseEntity<Any> {
        val response = userService.getUser(userId, platform)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/users/{userId}")
    fun updateUser(
        @PathVariable userId: Long,
        @RequestBody request:UpdateUserRequest
    ): ResponseEntity<UpdateUserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUser(userId, request))

    }

    @DeleteMapping("/users/withdraw/{userId}")
    fun deleteUser(@PathVariable userId: Long): ResponseEntity<Void> {
        userService.deleteUser(userId)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/password")
    fun updatePassword(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: UpdatePasswordRequest
    ) : ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updatePassword(userPrincipal, request))
    }

}