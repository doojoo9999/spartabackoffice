package com.teamsparta.spartabackoffice.infra.social.jwt

import co.touchlab.kermit.platformLogWriter
import com.teamsparta.spartabackoffice.domain.user.model.UserRole
import com.teamsparta.spartabackoffice.infra.social.model.SocialEntity
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
import java.time.Instant
import java.util.*

@Component
class JwtProvider(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour : Long,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val refreshTokenExpirationHour : Long,
) {

    companion object {
        private const val BEARER_TYPE = "bearer"
    }
    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            // key로 서명을 검증하고, 만료시간을 체크한다.
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)

        }
    }
    fun generateJwtDto(oAuth2User: OAuth2User, id: String, role:String,platform: String) : JwtDto {
        val accessTokenExpiresIn = Date(Date().time + accessTokenExpirationHour)
        val now = Instant.now()
        val expirationPeriod = java.time.Duration.ofHours(168)
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val claims:Claims = Jwts.claims()
            .add(mapOf("role" to role, "email" to oAuth2User.attributes["email"] as String,"platform" to platform))
            .build()
        val accessToken = Jwts.builder()
            .subject(id) // payload "sub": "id"
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key) // header "alg": "HS512"
            .compact()


        val refreshToken = Jwts.builder()
            .expiration(Date(Date().time+refreshTokenExpirationHour))
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