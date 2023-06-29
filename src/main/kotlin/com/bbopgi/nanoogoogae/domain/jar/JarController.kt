package com.bbopgi.nanoogoogae.domain.jar

import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDetailDto
import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDto
import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleSaveRequest
import com.bbopgi.nanoogoogae.domain.jar.service.CapsuleService
import com.bbopgi.nanoogoogae.domain.jar.dto.JarDto
import com.bbopgi.nanoogoogae.domain.jar.service.JarService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/jar")
@CrossOrigin(origins = ["*"])
class JarController(
    private val jarService: JarService,
    private val capsuleService: CapsuleService,
) {
    val mockDataCapsule = CapsuleDto(
        capsuleId = 1,
        authorNickname = "user-nickname",
        createdAt = Date(),
        emojiReply = "[TBD] emojiReply",
        isPublic = false,
        isRead = false,
        type = "normal",
        color = "red",
    )

    val mockData = JarDto(
        userNickname = "userNickname",
        capsules = listOf(mockDataCapsule),
        coin = 5,
    )

    val mockCapsule = CapsuleDetailDto(
        authorNickname = "userNickname",
        text = "편지 내용~~!@!~!@",
        createdAt = Date(),
        emojiReply = "[TBD] emojiReply",
        jarId = "jarId (=link path)",
        isPublic = false,
        isRead = false,
        type = "normal",
        color = "red",
        isReplied = false,
    )
/*
    @Operation(summary = "( *사용X ) 개발용 API.")
    @PostMapping
    fun create(@RequestBody payload: JarSaveRequest):ResponseEntity<Unit> {
        return ReponseEntity.ok().build()
//        return ResponseEntity.ok(jarService.create(payload))
    }
*/
    @GetMapping("/{jarId}")
    fun get(@PathVariable jarId: String): ResponseEntity<JarDto> {
        return ResponseEntity.ok(jarService.getJar(jarId))
    }
/*
    @Operation(summary = "유저 id에 해당하는 뽑기통 & 담겨진 캡슐 편지 정보 조회")
    fun getByUserId(@RequestParam userId: String): ResponseEntity<JarDto> {
        return ResponseEntity.ok(mockData)
    }
*/
    /*
    @Operation(summary = "( *사용X ) 유저가 탈퇴하는 경우 내부적으로 삭제 로직 진행 예정")
    @DeleteMapping("/{jarId}")
    fun delete(@PathVariable jarId: String): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
*/
    @PostMapping("/{jarId}")
    @Operation(summary = "편지 작성 API, 하단의 CapsuleSaveRequest DTO 참고")
    fun createCapsule(@PathVariable jarId: String, @RequestBody payload: CapsuleSaveRequest): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "캡슐 상세 조회 API")
    @ApiResponse(responseCode = "200", description = "하단의 CapsuleDetailDto DTO 참고")
    @GetMapping("/{jarId}/{capsuleId}")
    fun getCapsuleDetail(@PathVariable jarId: String, @PathVariable capsuleId: String): ResponseEntity<CapsuleDetailDto> {
        return ResponseEntity.ok(mockCapsule)
    }

    @PostMapping("/{jarId}/{capsuleId}/reply")
    @Operation(summary = "답장하기 버튼을 통한 편지 작성 API, gkeksdml CapsuleSaveRequest DTO 참고")
    fun replyCapsule(@PathVariable jarId: String,
                     @PathVariable capsuleId: String,
                     @RequestBody payload: CapsuleSaveRequest): ResponseEntity<Int> {
        // Return CREATED  with 1 as response body
        return ResponseEntity(1, HttpStatus.CREATED)
    }

    @DeleteMapping("/{jarId}/{capsuleId}")
    fun deleteCapsule(@PathVariable jarId: String, @PathVariable capsuleId: String): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }

}