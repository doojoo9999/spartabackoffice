package com.teamsparta.spartabackoffice.infra.social.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider {

    companion object {
        private const val AUTHORITIES_KEY = "auth"
        private const val BEARER_TYPE = "bearer"
        private const val ACCESS_TOKEN_EXPIRE_TIME = (1000 * 60 * 30)
        private const val REFRESH_TOKEN_EXPIRE_TIME = (1000 * 60 * 60 * 24 * 7)
    }

    @Value("\${auth.jwt.secret}")
    private lateinit var secretKeyConfig: String

    private val key: SecretKey by lazy {
        val secretKey = secretKeyConfig.repeat(2) // base64Encoded
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) as SecretKey
    }
    fun validateToken(token: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
        }
    }
    fun generateJwtDto(oAuth2User: OAuth2User) : JwtDto {
        val now = Date().time
        val accessTokenExpiresIn = Date(now + ACCESS_TOKEN_EXPIRE_TIME)

        val accessToken = Jwts.builder()
            .subject(oAuth2User.attributes["email"] as String) // payload "sub": "email"
            .expiration(accessTokenExpiresIn) // payload "exp": 1516239022 (예시)
            .signWith(key) // header "alg": "HS512"
            .compact()

        val refreshToken = Jwts.builder()
            .expiration(Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key)
            .compact()

        return JwtDto(
            grantType = BEARER_TYPE,
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessTokenExpiresIn = accessTokenExpiresIn.time
        )
    }




}