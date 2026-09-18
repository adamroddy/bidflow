package com.roddy.bidflow.auth.service

import com.roddy.bidflow.auth.dto.AuthRequest
import com.roddy.bidflow.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class AuthService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val encoder: JwtEncoder
) {

    fun authorizeUser(authRequest: AuthRequest): String {
        val user = userService.findByUsername(authRequest.username)

        when {
            user == null -> {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
            }

            !passwordEncoder.matches(authRequest.password, user.password) -> {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password not matched")
            }

            else -> return generateToken(user.username)
        }
    }

    private fun generateToken(username: String): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer("bidflow")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .subject(username)
            .build()

        return encoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}
