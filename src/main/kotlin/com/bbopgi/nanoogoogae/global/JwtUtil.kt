package com.bbopgi.nanoogoogae.global

import io.jsonwebtoken.Jwts
import java.util.*

class JwtUtil {
    companion object {
        fun createJwt(id:String, secretKey: String, expiredMs: Long):String {
            val claims = Jwts.claims()
            claims["id"] = id

            return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + expiredMs))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretKey)
                .compact()
        }
    }
}

