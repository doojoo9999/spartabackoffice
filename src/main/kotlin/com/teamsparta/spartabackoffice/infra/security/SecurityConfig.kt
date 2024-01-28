package com.teamsparta.spartabackoffice.infra.security

import com.teamsparta.spartabackoffice.infra.security.jwt.CustomAccessDeniedHandler
import com.teamsparta.spartabackoffice.infra.security.jwt.JwtAuthenticationFilter
import com.teamsparta.spartabackoffice.infra.social.jwt.SocialJwtAuthenticationFilter
import com.teamsparta.spartabackoffice.infra.social.service.CustomUserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val customUserDetailService: CustomUserDetailService, //소셜로그인
    private val socialJwtAuthenticationFilter: SocialJwtAuthenticationFilter,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) }
            .oauth2Login {
                it.userInfoEndpoint { u -> u.userService(customUserDetailService) }
                it.defaultSuccessUrl("/api/v1/login")
                it.failureUrl("/fail")
            }
            .authorizeHttpRequests {
                it.requestMatchers (
//                    TODO("수정 필요")
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api/**"
                ).permitAll()
                    .requestMatchers("/api/v1/**").authenticated()
                    //위 URI를 제외하고는 모두 인증과정을 거치겠음.
//                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(socialJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
                it.accessDeniedHandler(accessDeniedHandler)
            }
            .build()
    }



}