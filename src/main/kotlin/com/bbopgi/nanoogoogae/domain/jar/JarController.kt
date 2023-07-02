package com.bbopgi.nanoogoogae.domain.jar

import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDetailDto
import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleSaveRequest
import com.bbopgi.nanoogoogae.domain.jar.service.CapsuleService
import com.bbopgi.nanoogoogae.domain.jar.dto.JarDto
import com.bbopgi.nanoogoogae.domain.jar.service.JarService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/jar")
@CrossOrigin(origins = ["*"])
class JarController(
    private val jarService: JarService,
    private val capsuleService: CapsuleService,
) {
    /* Jar Controllers*/
    @GetMapping("/{jarId}")
    fun get(@PathVariable jarId: String): ResponseEntity<JarDto> {
        return ResponseEntity.ok(jarService.getJar(jarId))
    }
    /* Capsule Controllers*/
    @PostMapping("/{jarId}")
    @Operation(summary = "편지 작성 API. returns capsule id")
    fun createCapsule(
        authentication: Authentication?,
        @PathVariable jarId: String,
        @RequestBody payload: CapsuleSaveRequest
    ): ResponseEntity<String> {
        return ResponseEntity.ok(
            capsuleService.createCapsule(payload, jarId, authentication?.name)
        )
    }

    @Operation(
        summary = "캡슐 상세 조회 API",
        responses = [
            ApiResponse(responseCode = "200", description = "해당 캡슐 정보 반환"),
            ApiResponse(responseCode = "401", description = "로그인 필요"),
        ]
    )
    @ApiResponse(responseCode = "200")
    @GetMapping("/{jarId}/{capsuleId}")
    fun readCapsule(
        authentication: Authentication?,
        @PathVariable jarId: String,
        @PathVariable capsuleId: String,
    ): ResponseEntity<CapsuleDetailDto> {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        return ResponseEntity.ok(capsuleService.readCapsule(capsuleId, authentication.name))
    }

    @PostMapping("/{jarId}/{capsuleId}/reply")
    @Operation(summary = "답장하기 버튼을 통한 편지 작성 API")
    fun replyCapsule(
        authentication: Authentication,
        @PathVariable jarId: String,
        @PathVariable capsuleId: String,
        @RequestBody payload: CapsuleSaveRequest
    ): ResponseEntity<String?> {
        return ResponseEntity(
            capsuleService.replyCapsule(jarId, capsuleId, payload, authentication.name),
            HttpStatus.OK
        )
    }

    @DeleteMapping("/{jarId}/{capsuleId}")
    fun deleteCapsule(@PathVariable jarId: String, @PathVariable capsuleId: String): ResponseEntity<Unit> {
        return ResponseEntity(capsuleService.deleteCapsule(capsuleId), HttpStatus.OK)
    }
}