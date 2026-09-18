package com.roddy.bidflow.auth.controller

import com.roddy.bidflow.auth.dto.AuthRequest
import com.roddy.bidflow.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    fun generateToken(@RequestBody authRequest: AuthRequest): String {
        return authService.authorizeUser(authRequest)
    }
}
