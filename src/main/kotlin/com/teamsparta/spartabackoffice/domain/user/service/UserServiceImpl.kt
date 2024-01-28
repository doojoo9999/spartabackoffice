package com.teamsparta.spartabackoffice.domain.user.service

import com.teamsparta.spartabackoffice.domain.comment.repository.CommentRepository
import com.teamsparta.spartabackoffice.domain.exception.EmailNotFoundException
import com.teamsparta.spartabackoffice.domain.exception.ModelNotFoundException
import com.teamsparta.spartabackoffice.domain.post.repository.PostRepository
import com.teamsparta.spartabackoffice.domain.user.dto.request.LoginRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.SignUpRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdatePasswordRequest
import com.teamsparta.spartabackoffice.domain.user.dto.request.UpdateUserRequest
import com.teamsparta.spartabackoffice.domain.user.dto.response.UpdateUserResponse
import com.teamsparta.spartabackoffice.domain.user.dto.response.UserResponse
import com.teamsparta.spartabackoffice.domain.user.model.Platform
import com.teamsparta.spartabackoffice.domain.user.model.UserEntity
import com.teamsparta.spartabackoffice.domain.user.model.UserRole
import com.teamsparta.spartabackoffice.domain.user.model.toResponse
import com.teamsparta.spartabackoffice.domain.user.repository.UserRepository
import com.teamsparta.spartabackoffice.infra.security.UserPrincipal
import com.teamsparta.spartabackoffice.infra.security.jwt.JwtPlugin
import com.teamsparta.spartabackoffice.infra.social.repository.SocialRepository
import com.teamsparta.spartabackoffice.infra.util.ValidationUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val postRepository : PostRepository,
    private val commentRepository: CommentRepository,
    private val socialRepository: SocialRepository
) : UserService {
    override fun signUp(request: SignUpRequest): UserResponse {
        userRepository.findByEmail(request.email)?.let {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
        if (!ValidationUtil.isValidUsername(request.name)) throw IllegalArgumentException("사용자 이름은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.")
        if (!ValidationUtil.isValidPassword(request.password)) throw IllegalArgumentException("비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 합니다.")

        val user = UserEntity(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            platform = Platform.SPARTA,
            role = UserRole.STUDENT
        )
        userRepository.save(user)
        return user.toResponse()
    }

    override fun login(request: LoginRequest): Pair<UserResponse, String> {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 또는 비밀번호가 다릅니다.")
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("이메일 또는 비밀번호가 다릅니다.")
        }
        val token = jwtPlugin.generateAccessToken(user.id.toString(), user.email, user.role.toString(), user.platform.toString())
        return Pair(user.toResponse(), token)
    }

    override fun getUser(userId: Long, userPrincipal: UserPrincipal): Any {


        // 이렇게 하면 requestparam으로 플랫폼 정보를 받지 않아도 됨.
        // 하지만 userId가 각 테이블에서 관리되어 중복될 경우 어떻게 처리해야될 지 모르겠음
        val tokenUserId = userPrincipal.id

        val tokenUserPlatform = userPrincipal.platform

        when (tokenUserPlatform) {
            Platform.SPARTA -> {
                val checkUserId = userRepository.findByIdOrNull(tokenUserId)
                    ?: throw ModelNotFoundException("UserId", tokenUserId)

                return checkUserId.toResponse()
            }

            Platform.GOOGLE -> {
                val checkUserId2 = socialRepository.findByIdOrNull(tokenUserId)
                    ?: throw ModelNotFoundException("UserId", tokenUserId)

                return UserEntity(
                    email = checkUserId2.email,
                    name = checkUserId2.id.toString(),
                    role = checkUserId2.role,
                    platform = checkUserId2.platform,
                    password = "social login"
                )
            }

            else -> throw IllegalStateException("플랫폼 정보가 확인되지 않았습니다.")
        }

//        val authentication = SecurityContextHolder.getContext().authentication
//        if(authentication.principal !is UserPrincipal)
//            throw IllegalArgumentException("알 수 없는 사용자 타입입니다.")
//
//        val email = (authentication.principal as UserPrincipal).email
//        val user = userRepository.findById(userId)
//            .orElseThrow { EmailNotFoundException(email) }
//        return user.toResponse()
    }


    @Transactional
    override fun updateUser(
        userId: Long,
        request: UpdateUserRequest
    ): UpdateUserResponse {

        val user = userRepository.findByIdOrNull(userId)
            ?: throw IllegalArgumentException("유저 정보를 다시 확인해 주세요.")

        user.email = request.email

        userRepository.save(user)

        return UpdateUserResponse(user.email)

    }


    override fun deleteUser(userId: Long) {
        val authentication = SecurityContextHolder.getContext().authentication
        val userPrincipal = authentication.principal as UserPrincipal

        if (userPrincipal.id != userId) {
            throw IllegalArgumentException("요청한 사용자 ID와 토큰의 사용자 ID가 일치하지 않습니다.")
        }
        userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        }.also {

            userRepository.delete(it)
        }
    }

    override fun updatePassword(userPrincipal: UserPrincipal, request: UpdatePasswordRequest): Any {
        val user = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException ("UserId", userPrincipal.id)

        if (!passwordEncoder.matches(request.oldPassword, user.password)) {
            throw IllegalArgumentException ("비밀번호가 틀렸습니다.")
        }

        if (passwordEncoder.matches(request.newPassword, request.confirmPassword)) {
            throw IllegalArgumentException("새 비밀번호와 확인 비밀번호가 다릅니다.")
        }

        if (user.oldPasswords.any { passwordEncoder.matches(request.newPassword, it)}) {
            throw IllegalStateException("3회 안에 사용되었던 비밀번호를 재사용할 수 없습니다.")
        }

        user.password = passwordEncoder.encode(request.newPassword)
        user.oldPasswords.add(user.password)

        if (user.oldPasswords.size > 3) {
            user.oldPasswords.removeAt(0)
        }

        userRepository.save(user)

        return "비밀번호 변경 완료"
    }


}