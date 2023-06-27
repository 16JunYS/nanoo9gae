package com.bbopgi.nanoogoogae.global.entity

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.Date

@Document(collection = "capsule")
data class Capsule(
    @Id
    var capsuleId: Int,

    @Field("author_nickname")
    var authorNickname: String,

    @Field("author_id")
    var authorId: String,

    var text: String,

    @Field("created_at")
    var createdAt: Date,

    @Field("emoji_reply")
    var emojiReply: String,

    @Field("jar_id")
    var jarId: String,

    @Field("is_public")
    var isPrivate: Boolean,

    @Field("is_read")
    var isRead: Boolean = false,

    @Field("is_replied")
    var option_reply: Boolean = false,

    var type: String,

    var color: String,

)
