package com.bbopgi.nanoogoogae.domain.user

import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {

    fun insert(user: User): User?

    fun findByUserId(id: String): User?

    fun deleteByUserId(userId: String)

}