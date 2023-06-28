package com.bbopgi.nanoogoogae.domain.jar.service

import com.bbopgi.nanoogoogae.domain.jar.dto.JarDto
import com.bbopgi.nanoogoogae.global.entity.Jar
import com.bbopgi.nanoogoogae.global.entity.toDto
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
        val jar = jarRepository.findByJarId(jarId) ?: throw Exception("존재하지 않는 뽑기통입니다.")
        val user = userRepository.findByUserId(jar.userId) ?: throw Exception("존재하지 않는 뽑기통입니다.")
        val capsules = capsuleRepository.findByJarId(jar.jarId) ?: listOf()
        val capsuleDtos = capsules.map { it.toDto() }

        return JarDto(
            userNickname = jar.userNickname ?: throw Exception("존재하지 않는 뽑기통입니다."),
            capsules = capsuleDtos,
            coin = user.coin,
        )
    }

    fun createJar(userNickname: String, userId: String) {
        // create random 16 alphanumeric string
        var jarId = createJarId()
        while(getJar(jarId) != null) {
            jarId = createJarId()
        }

        jarRepository.insert(Jar(
            jarId = jarId,
            userNickname = userNickname,
            userId = jarId,
        ))
    }

    fun createJarId() = List(16) {
        (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")
}