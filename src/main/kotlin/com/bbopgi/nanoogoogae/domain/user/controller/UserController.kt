package com.bbopgi.nanoogoogae.domain.user.controller

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name="user controller", description = "controller for users")
class UserController {

    @GetMapping("{path1}")
    fun temp(@PathVariable path1: String, @Parameter(description = "param1")param: String):String {
        return path1 + param;
    }
}