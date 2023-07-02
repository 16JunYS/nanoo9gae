package com.bbopgi.nanoogoogae.global.configuration

import com.bbopgi.nanoogoogae.domain.user.UserService
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class JwtFilter(
    val userService: UserService,
    val secretKey: String,
): OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain
    ) {
        var token = request.getHeader("Authorization")
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        token = token.replace("Bearer ", "")
        if (isExpired(token, secretKey)) {
            // 만료된 토큰
            filterChain.doFilter(request, response)
            return
        }

        // UserName Token에서 추출
        var userName: String = getUserId(token, secretKey)

        // 권한 부여
        val authenticationToken: UsernamePasswordAuthenticationToken
        = UsernamePasswordAuthenticationToken(userName, null, listOf(SimpleGrantedAuthority("USER")))

        // Details 설정
        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
        filterChain.doFilter(request, response)
/*
        val token = request.getHeader("Authorization")?.replace("Bearer ", "")
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val authentication = jwtTokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
*/
    }

    companion object{
        fun isExpired(token: String, secretKey: String): Boolean {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.expiration
                .before(Date())
        }

        fun getUserId(token: String, secretKey: String):String {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .body.get("id", String::class.java)
        }
    }

}