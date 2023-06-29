package com.bbopgi.nanoogoogae.domain.user

import com.bbopgi.nanoogoogae.global.entity.User
import com.bbopgi.nanoogoogae.global.entity.toDto
import com.bbopgi.nanoogoogae.global.repository.UserRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
@Tag(name="User controller", description = "유저 관리 API")
class UserController(
    private val userService: UserService,
) {
    @Operation(
        summary = "사용자 계정 생성, 뽑기통 ID 반환",
        responses = [
            ApiResponse(responseCode = "201", description = "사용자 링크에 사용될 path(뽑기통 id) 반환"),
        ]
    )
    @PostMapping("/user")
    fun createAccount(@RequestBody payload: UserDto): ResponseEntity<String> {
        val jarId = userService.createUser(payload)

        return ResponseEntity<String>(jarId, HttpStatus.CREATED)
    }

    @Operation(summary = "유저 ID로 유저 정보 조회")
    @GetMapping("/user")
    fun getUser(@RequestParam id: String): ResponseEntity<UserDto?> {
        val user = userService.getUser(id)
        return ResponseEntity(user, HttpStatus.OK)
    }

    @Operation(summary = "유저 삭제(탈퇴)")
    @DeleteMapping("/user")
    fun deleteUser(@RequestParam id: String): ResponseEntity<Unit> {
        userService.deleteUser(id)

        return ResponseEntity.ok().build()
    }

    @Operation(summary="유저 가입 시 id 중복 체크")
    @GetMapping("/user/check")
    fun validateUserId(@RequestParam id: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.validateUserId(id))
    }

}