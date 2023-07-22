package com.bbopgi.nanoogoogae.domain.jar.service

import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDetailDto
import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleSaveRequest
import com.bbopgi.nanoogoogae.global.entity.Capsule
import com.bbopgi.nanoogoogae.global.entity.toDetailDto
import com.bbopgi.nanoogoogae.global.exception.NanoogoogaeException
import com.bbopgi.nanoogoogae.global.repository.CapsuleRepository
import com.bbopgi.nanoogoogae.global.repository.JarRepository
import com.bbopgi.nanoogoogae.global.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CapsuleService(
    private val capsuleRepository: CapsuleRepository,
    private val jarRepository: JarRepository,
    private val userRepository: UserRepository,
) {
    fun createCapsule(payload: CapsuleSaveRequest, jarId: String, userId: String?): String {
        var capsuleId = ObjectId().toString()
        while(capsuleRepository.findByCapsuleId(capsuleId) != null) {
            capsuleId = ObjectId().toString()
        }

        val capsule = Capsule(
            capsuleId = capsuleId,
            jarId = jarId,
            authorId = userId ?: "",
            authorNickname = payload.authorNickname,
            content = payload.content,
            isPublic = payload.isPublic,
            color = payload.color,
        )
        val ret = capsuleRepository.insert(capsule) ?: throw NanoogoogaeException("캡슐 생성에 실패했습니다.")

        if (userId != null) {
            val user = userRepository.findByUserId(userId)
            user!!.coin++
            userRepository.save(user)
        }

        return ret.capsuleId
    }

    fun replyCapsule(
        fromJarId: String, fromCapsuleId: String, payload: CapsuleSaveRequest,
        fromUserId: String
    ): String? {
        if (fromJarId != jarRepository.findByUserId(fromUserId)!!.jarId) {
            throw NanoogoogaeException("권한이 없습니다.")
        }

        val fromCapsule = capsuleRepository.findByCapsuleId(fromCapsuleId) ?: throw NanoogoogaeException("존재하지 않는 캡슐입니다.")
        if (fromCapsule.authorId == null || fromCapsule.authorId == "") {
            throw NanoogoogaeException("비회원이 보낸 캡슐입니다.")
        }

        val replyToUser = userRepository.findByUserId(fromCapsule.authorId!!)!!
        val capsule = capsuleRepository.insert(
            Capsule(
                capsuleId = ObjectId().toString(),
                jarId = jarRepository.findByUserId(replyToUser.userId)!!.jarId,
                authorId = fromUserId,
                authorNickname = payload.authorNickname,
                content = payload.content,
                isPublic = payload.isPublic,
                color = payload.color,
                replyFrom = fromCapsuleId,
            )
        ) ?: throw NanoogoogaeException("캡슐 생성에 실패했습니다.")

        val replyFromUser = userRepository.findByUserId(fromUserId)
        replyFromUser!!.coin++
        userRepository.save(replyFromUser)

        fromCapsule.replyTo = capsule.capsuleId
        capsuleRepository.save(fromCapsule)

        return capsule.capsuleId
    }

    fun readCapsule(capsuleId: String, userId: String, isRandom: Boolean = false): CapsuleDetailDto {
        val capsule = capsuleRepository.findByCapsuleId(capsuleId) ?: throw NanoogoogaeException("존재하지 않는 캡슐입니다.")

        // Capsule owner reads the capsule
        if (jarRepository.findByJarId(capsule.jarId)!!.userId == userId) {
            val user = userRepository.findByUserId(userId) ?: throw NanoogoogaeException("존재하지 않는 유저입니다.")

            if (user.coin < 2) {
                throw NanoogoogaeException("코인이 부족합니다.")
            }
            user.coin -= if (isRandom) 1 else 2
            userRepository.save(user)

            capsule.isRead = true
            capsuleRepository.save(capsule)
        }
        else if (!capsule.isRead && capsule.authorId != userId) {
            throw NanoogoogaeException("뽑기통 주인이 읽지 않았습니다.")
        }
        else if (capsule.isRead && !capsule.isPublic && capsule.authorId != userId) {
            throw NanoogoogaeException("비공개인 편지입니다.")
        }

        val capsuleDto = capsule.toDetailDto()
        if (capsule.replyTo != null) {
            capsuleDto.replyCapsule = capsuleRepository.findByCapsuleId(capsule.replyTo!!)!!.content
        }

        return capsuleDto
    }

    fun readRandomCapsule(jarId: String, userId: String): CapsuleDetailDto {
        val randomCapsules = capsuleRepository.findRandomCapsuleId(jarId)

        if (randomCapsules.isEmpty()) {
            throw NanoogoogaeException("읽을 캡슐이 없습니다.")
        }

        return readCapsule(randomCapsules[0].capsuleId, userId, true)
    }

    fun deleteCapsule(capsuleId: String) {
        capsuleRepository.deleteByCapsuleId(capsuleId)
    }
}