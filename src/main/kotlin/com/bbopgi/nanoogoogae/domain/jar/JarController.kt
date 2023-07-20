package com.bbopgi.nanoogoogae.domain.jar

import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleDetailDto
import com.bbopgi.nanoogoogae.domain.jar.dto.CapsuleSaveRequest
import com.bbopgi.nanoogoogae.domain.jar.service.CapsuleService
import com.bbopgi.nanoogoogae.domain.jar.dto.JarDto
import com.bbopgi.nanoogoogae.domain.jar.service.JarService
import com.bbopgi.nanoogoogae.global.common.CommonApiResponse
import com.bbopgi.nanoogoogae.global.common.Serializer
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/jar")
@CrossOrigin(origins = ["*"])
class JarController(
    private val jarService: JarService,
    private val capsuleService: CapsuleService,
) {
    /* Jar Controllers*/
    @GetMapping("/{jarId}")
    fun get(@PathVariable jarId: String): CommonApiResponse<JarDto> {
        return CommonApiResponse<JarDto>().success(jarService.getJar(jarId))
    }
    /* Capsule Controllers*/
    @PostMapping("/{jarId}")
    @Operation(summary = "편지 작성 API. returns capsule id")
    fun createCapsule(
        authentication: Authentication?,
        @PathVariable jarId: String,
        @RequestBody payload: CapsuleSaveRequest
    ): CommonApiResponse<*> {
        return try {
            val id = capsuleService.createCapsule(payload, jarId, authentication?.name)

            CommonApiResponse<Any>()
                .created(Serializer().buildMap("id" to id))
        } catch (e: Exception) {
            CommonApiResponse<Unit>().error(e.message)
        }
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
    ): CommonApiResponse<CapsuleDetailDto> {
        if (authentication == null) {
            return CommonApiResponse<CapsuleDetailDto>()
                .error(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.")
        }
        return CommonApiResponse<CapsuleDetailDto>()
            .success(capsuleService.readCapsule(capsuleId, authentication.name))
    }

    @Operation(
        summary = "랜덤 캡슐 상세 조회 API",
        responses = [
            ApiResponse(responseCode = "200", description = "해당 캡슐 정보 반환"),
            ApiResponse(responseCode = "401", description = "로그인 필요"),
        ]
    )
    @ApiResponse(responseCode = "200")
    @GetMapping("/{jarId}/random")
    fun readRandomCapsule(
        authentication: Authentication?,
        @PathVariable jarId: String,
    ): CommonApiResponse<CapsuleDetailDto> {
        if (authentication == null) {
            return CommonApiResponse<CapsuleDetailDto>()
                .error(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.")
        }
        return CommonApiResponse<CapsuleDetailDto>()
            .success(capsuleService.readRandomCapsule(jarId, authentication.name))
    }

    @PostMapping("/{jarId}/{capsuleId}/reply")
    @Operation(summary = "답장하기 버튼을 통한 편지 작성 API")
    fun replyCapsule(
        authentication: Authentication,
        @PathVariable jarId: String,
        @PathVariable capsuleId: String,
        @RequestBody payload: CapsuleSaveRequest
    ): CommonApiResponse<*> {
        val id = capsuleService.replyCapsule(jarId, capsuleId, payload, authentication.name)

        return CommonApiResponse<Any>()
            .success(Serializer().buildMap("id" to id))
    }

    @DeleteMapping("/{jarId}/{capsuleId}")
    fun deleteCapsule(@PathVariable jarId: String, @PathVariable capsuleId: String)
    : CommonApiResponse<Unit> {
        return CommonApiResponse<Unit>()
            .success(capsuleService.deleteCapsule(capsuleId))
    }
}