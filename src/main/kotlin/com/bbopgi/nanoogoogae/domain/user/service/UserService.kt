package com.bbopgi.nanoogoogae.domain.user.service

import com.bbopgi.nanoogoogae.domain.jar.service.JarService
import com.bbopgi.nanoogoogae.domain.user.dto.UserCreatePayload
import com.bbopgi.nanoogoogae.domain.user.dto.UserPublicDto
import com.bbopgi.nanoogoogae.domain.user.dto.toEntity
import com.bbopgi.nanoogoogae.global.JwtUtil
import com.bbopgi.nanoogoogae.global.entity.toPublicDto
import com.bbopgi.nanoogoogae.global.exception.NanoogoogaeException
import com.bbopgi.nanoogoogae.global.repository.JarRepository
import com.bbopgi.nanoogoogae.global.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jarService: JarService,
    private val jarRepository: JarRepository,
    @Value("\${jwt.secret.key}")
    private var secretKey: String = "",
) {
    private val expiredMs: Long = 1000 * 60 * 60 * 24 * 30L

    fun login(id: String, password: String): Pair<String, LocalDateTime?> {
        if (!validateIdPw(id, password)) {
            throw NanoogoogaeException("아이디 또는 비밀번호가 일치하지 않습니다.")
        }

        val user = userRepository.findByUserId(id)!!
        val datetime = user.lastLoginAt
        if (user.lastLoginAt != null) {
            user.lastLoginAt = LocalDateTime.now()
            userRepository.save(user)
        }

        return JwtUtil.createJwt(id, secretKey, expiredMs) to datetime
    }

    fun getUser(userId: String): UserPublicDto {
        val user = userRepository.findByUserId(userId) ?: throw NanoogoogaeException("존재하지 않는 유저입니다.")
        if (user.lastLoginAt == null) {
            user.lastLoginAt = LocalDateTime.now()
            userRepository.save(user)
        }

        return user.toPublicDto()
    }

    fun validateUserId(userId: String): Boolean {
        return userRepository.findByUserId(userId) == null
    }

    fun validateNickname(userNickname: String): Boolean {
        return userRepository.findByNickname(userNickname).isEmpty()
    }

    fun createUser(payload: UserCreatePayload): String {
        if (!validateUserId(payload.userId)) {
            throw NanoogoogaeException("이미 존재하는 아이디입니다")
        }
        if (!validateNickname(payload.nickname)) {
            throw NanoogoogaeException("이미 존재하는 닉네임입니다")
        }

        val jarId = jarService.createJar(
            userNickname = payload.nickname,
            userId = payload.userId,
        )

        userRepository.insert(payload.toEntity(jarId)) ?: throw NanoogoogaeException("유저 생성에 실패했습니다.")

        return jarId
    }

    fun deleteUser(userId: String) {
        val jar = jarRepository.findByUserId(userId) ?: throw NanoogoogaeException("뽑기통이 없는 유저입니다")
        userRepository.deleteByUserId(userId)
        jarRepository.deleteByJarId(jar.jarId)
    }

    fun validateIdPw(id: String, pw: String): Boolean {
        val user = userRepository.findByUserId(id) ?: throw NanoogoogaeException("존재하지 않는 유저입니다.")

        return user.password == pw
    }
}
