package com.bbopgi.nanoogoogae.domain.user

import com.bbopgi.nanoogoogae.global.entity.User
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

    val mockUser1 = UserDto("id1",
        "anonymousNickname",
        "passwd",
    "01012341234",
        5
    )
    @PostMapping("/user")
    fun createAccount(@RequestBody payload: User): ResponseEntity<String> {
        val userId = repo.insert(payload)?.userId

        return ResponseEntity<String>(userId, HttpStatus.CREATED)
    }

    @Operation(summary = "유저 ID로 유저 정보 조회")
    @GetMapping("/user")
    fun getUser(@RequestParam id: String): ResponseEntity<UserDto?> {
        return ResponseEntity(mockUser1, HttpStatus.OK);

        val user = repo.findByUserId(id)
        val statusCode =
            if (user != null) HttpStatus.OK
            else HttpStatus.INTERNAL_SERVER_ERROR

//        return ResponseEntity(user, statusCode)
    }

    @Operation(summary = "유저 삭제(탈퇴)")
    @DeleteMapping("/user")
    fun deleteUser(@RequestParam id: String) {
        repo.deleteByUserId(id)
    }

}