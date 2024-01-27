package com.teamsparta.spartabackoffice.domain.user.controller

import com.teamsparta.spartabackoffice.domain.user.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdatePasswordRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdateUserRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.domain.user.service.UserService
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Void> {
        userService.signUp(request)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<UserResponse> {
        val (memberResponse, token) = userService.login(request)
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .body(memberResponse)
    }

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        val userResponse = userService.getUser(userId)
        return ResponseEntity.ok(userResponse)
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