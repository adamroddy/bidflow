package com.roddy.bidflow.user

import com.roddy.bidflow.user.domain.User
import com.roddy.bidflow.user.dto.UserRequest
import com.roddy.bidflow.user.service.UserService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    private val logger = KotlinLogging.logger {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody request: UserRequest): User {
        return userService.createUser(request)
    }
}
