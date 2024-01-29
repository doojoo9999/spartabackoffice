package com.teamsparta.spartabackoffice.domain.user.service

import com.teamsparta.spartabackoffice.domain.comment.repository.CommentRepository
import com.teamsparta.spartabackoffice.domain.exception.EmailNotFoundException
import com.teamsparta.spartabackoffice.domain.exception.ModelNotFoundException
import com.teamsparta.spartabackoffice.domain.post.repository.PostRepository
import com.teamsparta.spartabackoffice.domain.user.dto.request.*
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
import com.teamsparta.spartabackoffice.infra.util.SecurityUtils.getAuthenticatedUser
import com.teamsparta.spartabackoffice.infra.util.SecurityUtils.validatePlatform
import com.teamsparta.spartabackoffice.infra.util.ValidationUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val socialRepository: SocialRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val postRepository : PostRepository,
    private val commentRepository: CommentRepository,
) : UserService {
    override fun signUp(request: SignUpRequest): UserResponse {
        userRepository.findByEmail(request.email)?.let {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
        if (!ValidationUtil.isValidUsername(request.name)) throw IllegalArgumentException("사용자 이름은 최소 2자 이상, 8자 이하이며 한글(가~힣)로 구성되어야 합니다.")
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

    override fun getUser(id: Long, platform: String): Any {
        val userPrincipal = getAuthenticatedUser()
        validatePlatform(userPrincipal, platform)
        val email = userPrincipal.email
        return when(platform) {
            "GOOGLE" -> {
                val socialUser = socialRepository.findById(id)
                    .orElseThrow { EmailNotFoundException(email) }
                socialUser.toResponse()
            }
            "SPARTA" -> {
                val user = userRepository.findById(id)
                    .orElseThrow { EmailNotFoundException(email) }
                user.toResponse()
            }
            else -> throw IllegalArgumentException("알 수 없는 사용자 타입입니다.")
        }
            }


//       // 이렇게 하면 requestparam으로 플랫폼 정보를 받지 않아도 됨.
//      // 하지만 userId가 각 테이블에서 관리되어 중복될 경우 어떻게 처리해야될 지 모르겠음
//       val tokenUserId = userPrincipal.id
//
//       val tokenUserPlatform = userPrincipal.platform
//
//       when (tokenUserPlatform) {
//           Platform.SPARTA -> {
//               val checkUserId = userRepository.findByIdOrNull(tokenUserId)
//                   ?: throw ModelNotFoundException("UserId", tokenUserId)
//
//               return checkUserId.toResponse()
//           }
//
//           Platform.GOOGLE -> {
//               val checkUserId = socialRepository.findByIdOrNull(tokenUserId)
//                   ?: throw ModelNotFoundException("UserId", tokenUserId)
//
//               return checkUserId.toResponse()
//           }
//
//           else -> throw IllegalStateException("플랫폼 정보가 확인되지 않았습니다.")
//       }



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


    override fun deleteUser(id: Long, platform: String): Any {
        val userPrincipal = getAuthenticatedUser()
        validatePlatform(userPrincipal, platform)

        return when(platform) {
            "GOOGLE" -> {
                val socialUser = socialRepository.findById(id)
                    .orElseThrow { IllegalArgumentException("해당 유저를 찾을 수 없습니다.") }
                socialRepository.delete(socialUser)
            }
            "SPARTA" -> {
                val user = userRepository.findById(id)
                    .orElseThrow { IllegalArgumentException("해당 유저를 찾을 수 없습니다.") }
                userRepository.delete(user)
            }
            else -> throw IllegalArgumentException("알 수 없는 사용자 타입입니다.")
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

    @Transactional
    override fun changeUserRole(
        userPrincipal: UserPrincipal,
        userId: Long,
        request: ChangeRoleRequest
    ): UserResponse {

        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException ("User Id", userId)

        user.role = request.newRole

        return userRepository.save(user).toResponse()
    }


}