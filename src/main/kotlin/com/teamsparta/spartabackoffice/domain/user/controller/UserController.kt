package com.teamsparta.spartabackoffice.domain.user.controller

import com.teamsparta.spartabackoffice.domain.user.dto.request.*
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserRoleResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.domain.user.model.Platform
import com.teamsparta.spartabackoffice.domain.user.service.UserService
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import com.teamsparta.spartabackoffice.infra.social.jwt.JwtDto
import com.teamsparta.spartabackoffice.infra.social.service.SocialService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
    fun socialLogin(@AuthenticationPrincipal oAuth2User: OAuth2User?): ResponseEntity<JwtDto> {
        if (oAuth2User == null) {
            throw IllegalArgumentException("인증된 사용자가 없습니다")
        }
        return ResponseEntity.ok(socialService.socialLogin(oAuth2User))
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun getUser(@PathVariable userId: Long, @RequestParam platform: String): ResponseEntity<Any> {
        val response = userService.getUser(userId, platform)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/users/{userId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun updateUser(
        @PathVariable userId: Long,
        @RequestBody request:UpdateUserRequest
    ): ResponseEntity<UpdateUserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUser(userId, request))

    }

    @DeleteMapping("/users/withdraw/{userId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun deleteUser(@PathVariable id: Long, platform: String): ResponseEntity<Void> {
        userService.deleteUser(id, platform)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/password")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN') or hasRole('TUTOR')")
    fun updatePassword(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: UpdatePasswordRequest
    ) : ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updatePassword(userPrincipal, request))
    }

    @PutMapping("/users/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    fun changeUserRole(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable userId : Long,
        @RequestBody request: ChangeRoleRequest
    ) : ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.changeUserRole(userPrincipal, userId, request))
    }


}