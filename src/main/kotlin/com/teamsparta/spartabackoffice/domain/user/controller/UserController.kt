package com.teamsparta.spartabackoffice.domain.user.controller

import com.teamsparta.spartabackoffice.domain.user.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.domain.user.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
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

    @DeleteMapping("/users/withdraw/{userId}")
    fun deleteUser(@PathVariable userId: Long): ResponseEntity<Void> {
        userService.deleteUser(userId)
        return ResponseEntity.noContent().build()
    }


}