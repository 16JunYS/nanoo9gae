package com.bbopgi.nanoogoogae.domain.jar.service

import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDetailDto
import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleSaveRequest
import com.bbopgi.nanoogoogae.global.entity.Capsule
import com.bbopgi.nanoogoogae.global.entity.toDetailDto
import com.bbopgi.nanoogoogae.global.repository.CapsuleRepository
import com.bbopgi.nanoogoogae.global.repository.JarRepository
import com.bbopgi.nanoogoogae.global.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CapsuleService(
    private val capsuleRepository: CapsuleRepository,
    private val jarRepository: JarRepository,
    private val userRepository: UserRepository,
) {
    fun createCapsule(payload: CapsuleSaveRequest, jarId: String):String {
        val capsule = Capsule(
            capsuleId = ObjectId().toString(),
            jarId = jarId,
            authorId = payload.userId,
            authorNickname = payload.authorNickname,
            content = payload.content,
            isPublic = payload.isPublic,
            color = payload.color,
        )
        val ret = capsuleRepository.insert(capsule) ?: throw Exception("캡슐 생성에 실패했습니다.")
        return ret.capsuleId
    }

    fun replyCapsule(fromJarId: String, capsuleId: String, payload: CapsuleSaveRequest): String? {
        val fromCapsule = capsuleRepository.findByCapsuleId(capsuleId) ?: throw Exception("존재하지 않는 캡슐입니다.")

        if (fromCapsule.authorId == null) {
            return null
        }

        val replyToUser = userRepository.findByUserId(fromCapsule.authorId!!)!!

        var capsule = Capsule(
            capsuleId = ObjectId().toString(),
            jarId = jarRepository.findByUserId(replyToUser.userId)!!.jarId,
            authorId = payload.userId,
            authorNickname = payload.authorNickname,
            content = payload.content,
            isPublic = payload.isPublic,
            color = payload.color,
            replyFrom = capsuleId,
        )
        val ret = capsuleRepository.insert(capsule) ?: throw Exception("캡슐 생성에 실패했습니다.")

        return ret.capsuleId
    }

    fun readCapsule(capsuleId: String): CapsuleDetailDto {
        var capsule = capsuleRepository.findByCapsuleId(capsuleId) ?: throw Exception("존재하지 않는 캡슐입니다.")
        capsule.isRead = true
        capsuleRepository.save(capsule)

        return capsule.toDetailDto()
    }

    fun deleteCapsule(capsuleId: String) {
        capsuleRepository.deleteByCapsuleId(capsuleId)
    }
}