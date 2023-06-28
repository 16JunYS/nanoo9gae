package com.bbopgi.nanoogoogae.global.repository

import com.bbopgi.nanoogoogae.global.entity.Jar
import org.springframework.data.mongodb.repository.MongoRepository

interface JarRepository: MongoRepository<Jar, String> {
    fun insert(jar: Jar): Jar?

    fun findByJarId(jarId: String): Jar?

    fun findByUserId(userId: String): Jar?

    fun deleteByJarId(jarId: String)
}