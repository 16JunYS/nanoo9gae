package com.bbopgi.nanoogoogae.domain.jar.service

import com.bbopgi.nanoogoogae.domain.jar.dto.JarDto
import com.bbopgi.nanoogoogae.global.entity.Jar
import com.bbopgi.nanoogoogae.global.entity.toDto
import com.bbopgi.nanoogoogae.global.exception.NanoogoogaeException
import com.bbopgi.nanoogoogae.global.repository.CapsuleRepository
import com.bbopgi.nanoogoogae.global.repository.JarRepository
import com.bbopgi.nanoogoogae.global.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class JarService(
    private val jarRepository: JarRepository,
    private val capsuleRepository: CapsuleRepository,
    private val userRepository: UserRepository,
) {

    fun getJar(jarId: String): JarDto {
        val jar = jarRepository.findByJarId(jarId) ?: throw NanoogoogaeException("존재하지 않는 뽑기통입니다.")
        val user = userRepository.findByUserId(jar.userId) ?: throw NanoogoogaeException("존재하지 않는 뽑기통입니다.")
        val capsules = capsuleRepository.findByJarId(jar.jarId)
        val capsuleDtos = capsules.map { it.toDto() }

        return JarDto(
            userNickname = jar.userNickname,
            capsules = capsuleDtos,
            coin = user.coin,
        )
    }

    fun getJarIdByUserId(userId: String): String {
        val jar = jarRepository.findByUserId(userId) ?: throw NanoogoogaeException("존재하지 않는 뽑기통입니다.")
        return jar.jarId
    }

    fun createJar(userNickname: String, userId: String): String {
        // create random 16 alphanumeric string
        var jarId = createJarId()
        while(jarRepository.findByJarId(jarId) != null) {
            jarId = createJarId()
        }

        var jar = jarRepository.insert(Jar(
            jarId = jarId,
            userNickname = userNickname,
            userId = userId,
        )) ?: throw NanoogoogaeException("뽑기통 생성에 실패했습니다.")

        return jarId
    }

    fun deleteJar(jarId: String) {
        capsuleRepository.deleteByJarId(jarId)
        jarRepository.deleteByJarId(jarId)
    }

    fun createJarId() = List(16) {
        (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")
}