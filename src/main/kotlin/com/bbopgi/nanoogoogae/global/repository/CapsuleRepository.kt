package com.bbopgi.nanoogoogae.global.repository

import com.bbopgi.nanoogoogae.global.entity.Capsule
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface CapsuleRepository: MongoRepository<Capsule, String> {

    fun insert(capsule: Capsule): Capsule?

    fun findByJarId(jarId: String): List<Capsule>

    fun findByCapsuleId(capsuleId: String): Capsule?

    fun findByAuthorIdAndReplyFrom(authorId: String, replyFrom: String): List<Capsule>

    fun deleteByCapsuleId(capsuleId: String)

    fun deleteByJarId(jarId: String)

    @Aggregation(pipeline = [
        "{'\$match': {'is_read': false}}",
//        "{'\$rand': {}}",
        "{'\$sample': {size: 1}}"
    ])
    fun findRandomCapsuleId(jarId: String): List<Capsule>
}