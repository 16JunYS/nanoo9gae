package com.bbopgi.nanoogoogae.global.repository

import com.bbopgi.nanoogoogae.global.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {

    fun insert(user: User): User?

    fun findByUserId(id: String): User?

    fun deleteByUserId(userId: String)

    fun findByNickname(nickname: String): List<User>

}