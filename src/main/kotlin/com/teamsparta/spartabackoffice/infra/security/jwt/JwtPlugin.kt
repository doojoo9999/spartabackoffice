package com.teamsparta.gogocard.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*

@Component
class JwtPlugin (
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour : Long,
){

    // try catch 대신 result 형태로 exception 처리를 할 수 있다.
    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            // key로 서명을 검증하고, 만료시간을 체크한다.
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)

        }
    }

    fun generateAccessToken(subject: String, email: String, role: String): String {
        //subject, 만료기간과 role을 설정한다.
        return generateToken(subject, email, role, java.time.Duration.ofHours(accessTokenExpirationHour))

    }

    fun generateToken(subject: String, email: String, role:String, expirationPeriod: java.time.Duration): String {
        //custom claim을 설정한다.
        val claims:Claims = Jwts.claims()
            .add(mapOf("role" to role, "email" to email))
            .build()

        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val expirationPeriod = java.time.Duration.ofHours(168)

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()

    }
}