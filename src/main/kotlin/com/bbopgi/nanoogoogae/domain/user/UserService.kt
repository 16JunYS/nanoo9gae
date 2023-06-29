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
) {

    fun getUser(userId: String): UserDto {
        val user = userRepository.findByUserId(userId) ?: throw Exception("존재하지 않는 유저입니다.")
        return user.toDto()
    }

    fun createUser(userDto: UserDto): String {
        val user = userRepository.insert(userDto.toEntity()) ?: throw Exception("유저 생성에 실패했습니다.")
        val jarId = jarService.createJar(
            userNickname = user.nickname,
            userId = user.userId,
        )

        return jarId
    }
}
