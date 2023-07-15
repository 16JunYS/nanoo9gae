package com.bbopgi.nanoogoogae.domain.user

import com.bbopgi.nanoogoogae.domain.jar.service.JarService
import com.bbopgi.nanoogoogae.domain.user.dto.*
import com.bbopgi.nanoogoogae.domain.user.service.UserService
import com.bbopgi.nanoogoogae.global.common.CommonApiResponse
import com.bbopgi.nanoogoogae.global.common.Serializer
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.lang.Exception

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
    fun createAccount(@RequestBody payload: UserCreatePayload): CommonApiResponse<*> {
        val jarId = userService.createUser(payload)
            ?: return CommonApiResponse<Unit>()
                .error("이미 존재하는 아이디입니다.")

        return CommonApiResponse<Any>()
            .created(Serializer().buildMap("id" to jarId))
    }

    @Operation(
        summary = "로그인",
        responses = [
        ApiResponse(responseCode = "200", description = "JWT 토큰과 뽑기통 ID 반환"),
//        ApiResponse(responseCode = "401", description = "로그인 실패"),
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody payload: UserLoginRequest): CommonApiResponse<*> {
        return try {
            val jwt = userService.login(payload.id, payload.password)
            val jarId = jarService.getJarIdByUserId(payload.id)

            return CommonApiResponse<UserLoginResponse>().success(UserLoginResponse(token = jwt, jarId = jarId))
        } catch (e: Exception) {
            CommonApiResponse<Unit>().error(e.message)
        }
    }

    @Operation(summary = "유저 토큰 또는 id로 유저 정보 조회")
    @GetMapping
    fun getUser(
        authentication: Authentication?,
        @RequestParam(required = false) id: String ?= "",
    ): CommonApiResponse<UserPublicDto> {
        return if (authentication != null) {
            CommonApiResponse<UserPublicDto>()
                .success(userService.getUser(authentication.name))
        } else {
            CommonApiResponse<UserPublicDto>()
                .success(userService.getUser(id!!))
        }
    }

    @Operation(summary = "유저 삭제(탈퇴)")
    @DeleteMapping
    fun deleteUser(
        authentication: Authentication,
    ): CommonApiResponse<Unit> {
        userService.deleteUser(authentication.name)

        return CommonApiResponse<Unit>().success()
    }

    @Operation(summary="유저 가입 시 id 중복 체크")
    @GetMapping("/check")
    fun validateUserId(@RequestParam id: String): CommonApiResponse<*> {
        return CommonApiResponse<Any>()
            .success(Serializer().buildMap("success" to userService.validateUserId(id)))
    }

}