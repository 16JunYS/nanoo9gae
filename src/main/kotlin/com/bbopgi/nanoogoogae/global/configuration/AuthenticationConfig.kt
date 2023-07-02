package com.bbopgi.nanoogoogae.global.configuration

import com.bbopgi.nanoogoogae.domain.user.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity // 모든 API에 인증이 필요
class AuthenticationConfig(
    private val userService: UserService,
    @Value("\${jwt.secret.key}")
    private val secretKey: String,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
            .csrf{ csrf -> csrf.disable()}
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/**").permitAll()
            }
            .formLogin(withDefaults())
            .addFilterBefore(JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}