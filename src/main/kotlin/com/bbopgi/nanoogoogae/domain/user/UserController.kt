package com.bbopgi.nanoogoogae.domain.user

import com.bbopgi.nanoogoogae.global.entity.User
import com.bbopgi.nanoogoogae.global.entity.toDto
import com.bbopgi.nanoogoogae.global.repository.UserRepository
import io.swagger.v3.oas.annotations.Operation
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
    // TEmp
    @Autowired val repo: UserRepository
) {
    @Operation(summary = "사용자 계정 생성, 뽑기통 ID 반환")
    @PostMapping("/user")
    fun createAccount(@RequestBody payload: User): ResponseEntity<String> {
        val userId = repo.insert(payload)?.userId

        return ResponseEntity<String>(userId, HttpStatus.CREATED)
    }

    @Operation(summary = "유저 ID로 유저 정보 조회")
    @GetMapping("/user")
    fun getUser(@RequestParam id: String): ResponseEntity<UserDto?> {
        val user = repo.findByUserId(id) ?: return ResponseEntity(null, HttpStatus.NOT_FOUND)
        return ResponseEntity(user.toDto(), HttpStatus.OK)
    }

    @Operation(summary = "유저 삭제(탈퇴)")
    @DeleteMapping("/user")
    fun deleteUser(@RequestParam id: String) {
        repo.deleteByUserId(id)
    }

}