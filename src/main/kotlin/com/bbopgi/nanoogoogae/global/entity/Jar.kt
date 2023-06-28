package com.bbopgi.nanoogoogae.global.entity

import com.bbopgi.nanoogoogae.domain.jar.dto.JarDto
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "jar")
data class Jar(
    @Field("jar_id")
    @field:Schema(description = "뽑기통 링크")
    var jarId: String,

    @Field("user_nickname")
    var userNickname: String,

    @Field("user_id")
    var userId: String,
)
