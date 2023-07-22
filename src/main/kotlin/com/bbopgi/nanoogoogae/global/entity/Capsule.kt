package com.bbopgi.nanoogoogae.global.entity

import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDetailDto
import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "capsule")
data class Capsule(
    @Id
    var capsuleId: String,

    @Field("author_nickname")
    var authorNickname: String,

    @Field("author_id")
    var authorId: String?,

    var content: String,

    @Field("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Field("emoji_reply")
    var emojiReply: String?=null,

    @Field("jar_id")
    var jarId: String,

    @Field("is_public")
    var isPublic: Boolean,

    @Field("is_read")
    var isRead: Boolean = false,

    @Field("reply_from")
    var replyFrom: String? = null,

    @Field("reply_to")
    var replyTo: String? = null,

    var color: String,
)

fun Capsule.toDto() = CapsuleDto(
    capsuleId = this.capsuleId,
    authorNickname = this.authorNickname,
    createdAt = this.createdAt,
    emojiReply = this.emojiReply,
    isPublic = this.isPublic,
    isRead = this.isRead,
    type = if (this.replyFrom == null) "normal" else "reply",
    color = this.color,
)

fun Capsule.toDetailDto() = CapsuleDetailDto(
    capsuleId = this.capsuleId,
    authorNickname = this.authorNickname,
    authorId = this.authorId,
    content = this.content,
    createdAt = this.createdAt,
    emojiReply = this.emojiReply,
    jarId = this.jarId,
    isPublic = this.isPublic,
    isRead = this.isRead,
    type = if (this.replyFrom == null) "normal" else "reply",
    isReplied = this.replyTo != null,
    color = this.color,
)