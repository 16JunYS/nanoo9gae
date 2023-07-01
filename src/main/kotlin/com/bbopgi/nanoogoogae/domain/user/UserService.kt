package com.bbopgi.nanoogoogae.domain.user

import com.bbopgi.nanoogoogae.domain.jar.service.JarService
import com.bbopgi.nanoogoogae.global.entity.toDto
import com.bbopgi.nanoogoogae.global.repository.JarRepository
import com.bbopgi.nanoogoogae.global.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jarService: JarService,
    private val jarRepository: JarRepository,
) {

    fun getUser(userId: String): UserDto {
        val user = userRepository.findByUserId(userId) ?: throw Exception("존재하지 않는 유저입니다.")
        return user.toDto()
    }

    fun validateUserId(userId: String): Boolean {
        return userRepository.findByUserId(userId) == null
    }

    fun createUser(payload: UserCreatePayload): String? {
        if (!validateUserId(payload.userId)) {
            return null
        }

        val jarId = jarService.createJar(
            userNickname = payload.nickname,
            userId = payload.userId,
        )

        userRepository.insert(payload.toEntity(jarId)) ?: throw Exception("유저 생성에 실패했습니다.")

        return jarId
    }

    fun deleteUser(userId: String) {
        val jar = jarRepository.findByUserId(userId) ?: throw Exception("뽑기통이 없는 유저입니다")
        userRepository.deleteByUserId(userId)
        jarRepository.deleteByJarId(jar.jarId)
    }
}
