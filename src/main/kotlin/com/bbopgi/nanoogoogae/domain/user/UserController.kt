package com.bbopgi.nanoogoogae.domain.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@Tag(name="User controller", description = "유저 관리 API")
class UserController(
    @Autowired val repo: UserRepository
) {

    @PostMapping("/user")
    fun createAccount(@RequestBody user: User) {
        val user = repo.insert(user)

        if (user == null) ResponseEntity<Unit>(HttpStatus.INTERNAL_SERVER_ERROR)
        else ResponseEntity<String?>(user.userId, HttpStatus.CREATED)
    }

    @Operation(summary = "유저 ID로 유저 정보 조회")
    @GetMapping("/user")
    fun getUser(@RequestParam id: String): ResponseEntity<User?> {
        val user = repo.findByUserId(id)
        val statusCode =
            if (user != null) HttpStatus.OK
            else HttpStatus.INTERNAL_SERVER_ERROR

        return ResponseEntity(user, statusCode)
    }

    @Operation(summary = "유저 삭제(탈퇴)")
    @DeleteMapping("/user")
    fun deleteUser(@RequestParam id: String) = repo.deleteByUserId(id);

    fun getAllUsers() = repo.findAll()

    @Operation(summary = "===임시 테스트 API 입니다.====")
    @GetMapping("{path1}")
    fun temp(@PathVariable path1: String, @Parameter(description = "param1")param: String?):String {
        return path1 + param
    }
}