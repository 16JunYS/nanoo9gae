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
@Transactional
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
        fromJarId: String, capsuleId: String, payload: CapsuleSaveRequest,
        userId: String
    ): String? {
        // [TODO] check if logged user matches with the jar owner

        val fromCapsule = capsuleRepository.findByCapsuleId(capsuleId) ?: throw NanoogoogaeException("존재하지 않는 캡슐입니다.")
        if (fromCapsule.authorId == null || fromCapsule.authorId == "") {
            return null
        }

        val replyToUser = userRepository.findByUserId(fromCapsule.authorId!!)!!

        var capsule = Capsule(
            capsuleId = ObjectId().toString(),
            jarId = jarRepository.findByUserId(replyToUser.userId)!!.jarId,
            authorId = userId,
            authorNickname = payload.authorNickname,
            content = payload.content,
            isPublic = payload.isPublic,
            color = payload.color,
            replyFrom = capsuleId,
        )
        val ret = capsuleRepository.insert(capsule) ?: throw NanoogoogaeException("캡슐 생성에 실패했습니다.")
        var replyFromUser = userRepository.findByUserId(userId)
        replyFromUser!!.coin++
        userRepository.save(replyFromUser)

        return ret.capsuleId
    }

    fun readCapsule(capsuleId: String, userId: String): CapsuleDetailDto {
        val capsule = capsuleRepository.findByCapsuleId(capsuleId) ?: throw NanoogoogaeException("존재하지 않는 캡슐입니다.")

        // Capsule owner reads the capsule
        if (jarRepository.findByJarId(capsule.jarId)!!.userId == userId) {
            val user = userRepository.findByUserId(userId) ?: throw NanoogoogaeException("존재하지 않는 유저입니다.")

            if (user.coin < 2) {
                throw NanoogoogaeException("코인이 부족합니다.")
            }
            user.coin -= 2
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

        return capsule.toDetailDto()
    }

    fun deleteCapsule(capsuleId: String) {
        capsuleRepository.deleteByCapsuleId(capsuleId)
    }
}