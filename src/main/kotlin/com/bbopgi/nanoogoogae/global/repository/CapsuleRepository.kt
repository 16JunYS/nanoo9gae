package com.bbopgi.nanoogoogae.global.repository

import com.bbopgi.nanoogoogae.global.entity.Capsule
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface CapsuleRepository: MongoRepository<Capsule, String> {

    fun insert(capsule: Capsule): Capsule?

    fun findByJarId(jarId: String): List<Capsule>

    fun findByCapsuleId(capsuleId: String): Capsule?

    fun deleteByCapsuleId(capsuleId: String)

    fun deleteByJarId(jarId: String)

    @Aggregation(pipeline = [
        "{'\$match': {'jar_id': ?0}}",
        "{'\$match': {'is_read': false}}",
        // [TODO] sample works when collection contains over 100 documents
//        "{'\$sample': {size: 1}}"
    ])
    fun findUnreadRandomCapsules(jarId: String): List<Capsule>

    @Aggregation(pipeline = [
        "{'\$match': {'jar_id': ?0}}",
        "{'\$match': {'is_read': true}}",
        "{'\$match': {'is_public': true}}",
    ])
    fun findReadRandomCapsules(jarId: String): List<Capsule>
}