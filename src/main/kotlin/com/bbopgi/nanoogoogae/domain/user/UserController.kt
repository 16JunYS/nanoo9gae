package com.bbopgi.nanoogoogae.domain.user

import com.bbopgi.nanoogoogae.domain.jar.service.JarService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/user")
@Tag(name="User controller", description = "유저 관리 API")
class UserController(
    private val userService: UserService,
    private val jarService: JarService,
) {
    @Operation(
        summary = "사용자 계정 생성, 뽑기통 ID 반환",
        responses = [
            ApiResponse(responseCode = "201", description = "사용자 링크에 사용될 path(뽑기통 id) 반환"),
            ApiResponse(responseCode = "400", description = "중복 아이디인 경우"),
        ]
    )
    @PostMapping
    fun createAccount(@RequestBody payload: UserCreatePayload): ResponseEntity<String> {
        val jarId = userService.createUser(payload)
            ?: return ResponseEntity<String>("이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST)

        return ResponseEntity<String>(jarId, HttpStatus.CREATED)
    }

    @Operation(
        summary = "로그인",
        responses = [
        ApiResponse(responseCode = "200", description = "JWT 토큰과 뽑기통 ID 반환"),
//        ApiResponse(responseCode = "401", description = "로그인 실패"),
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody payload: UserLoginRequest): ResponseEntity<UserLoginResponse> {
        val jwt = userService.login(payload.id, payload.password)
        val jarId = jarService.getJarIdByUserId(payload.id)

        return ResponseEntity(UserLoginResponse(token = jwt, jarId = jarId), HttpStatus.OK)
    }

    @Operation(summary = "유저 토큰 또는 id로 유저 정보 조회")
    @GetMapping
    fun getUser(
        authentication: Authentication?,
        @RequestParam(required = false) id: String ?= "",
    ): ResponseEntity<UserDto?> {
        return if (authentication != null) {
            ResponseEntity(userService.getUser(authentication.name), HttpStatus.OK)
        } else {
            ResponseEntity(userService.getUser(id!!), HttpStatus.OK)
        }
    }

    @Operation(summary = "유저 삭제(탈퇴)")
    @DeleteMapping
    fun deleteUser(
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        userService.deleteUser(authentication.name)

        return ResponseEntity.ok().build()
    }

    @Operation(summary="유저 가입 시 id 중복 체크")
    @GetMapping("/check")
    fun validateUserId(@RequestParam id: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.validateUserId(id))
    }

}