package com.bbopgi.nanoogoogae.domain.jar

import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDetailDto
import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleCreatePayload
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
    /* Jar Controllers*/
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
    /* Capsule Controllers*/
    @PostMapping("/{jarId}")
    @Operation(summary = "편지 작성 API. returns capsule id")
    fun createCapsule(@PathVariable jarId: String, @RequestBody payload: CapsuleCreatePayload): ResponseEntity<String> {
        return ResponseEntity.ok(capsuleService.createCapsule(payload, jarId))
    }

    @Operation(summary = "캡슐 상세 조회 API")
    @ApiResponse(responseCode = "200")
    @GetMapping("/{jarId}/{capsuleId}")
    fun readCapsule(@PathVariable jarId: String, @PathVariable capsuleId: String): ResponseEntity<CapsuleDetailDto> {
        return ResponseEntity.ok(capsuleService.readCapsule(capsuleId))
    }

    @PostMapping("/{jarId}/{capsuleId}/reply")
    @Operation(summary = "답장하기 버튼을 통한 편지 작성 API, mock API")
    fun replyCapsule(@PathVariable jarId: String,
                     @PathVariable capsuleId: String,
                     @RequestBody payload: CapsuleCreatePayload): ResponseEntity<String?> {
        return ResponseEntity(capsuleService.replyCapsule(jarId, capsuleId, payload), HttpStatus.OK)
    }

    @DeleteMapping("/{jarId}/{capsuleId}")
    fun deleteCapsule(@PathVariable jarId: String, @PathVariable capsuleId: String): ResponseEntity<Unit> {
        return ResponseEntity(capsuleService.deleteCapsule(capsuleId), HttpStatus.OK)
    }

}