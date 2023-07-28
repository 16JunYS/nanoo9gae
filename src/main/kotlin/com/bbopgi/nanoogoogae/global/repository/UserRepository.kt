package com.bbopgi.nanoogoogae.global.repository

import com.bbopgi.nanoogoogae.global.entity.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface UserRepository: MongoRepository<User, String> {

    fun insert(user: User): User?

    fun findByUserId(id: String): User?

    fun deleteByUserId(userId: String)

    fun findByNickname(nickname: String): List<User>

    fun findLastLoginAtByUserId(userId: String): String?
}