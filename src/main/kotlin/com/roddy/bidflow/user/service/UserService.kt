package com.roddy.bidflow.user.service

import com.roddy.bidflow.user.domain.User
import com.roddy.bidflow.user.dto.UserRequest
import com.roddy.bidflow.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(request: UserRequest): User {
        val hashedPassword = passwordEncoder.encode(request.password)
            ?: throw IllegalStateException("Password encoding failed")

        val newUser = User(
            username = request.username,
            password = hashedPassword,
            email = request.email
        )
        return userRepository.save(newUser)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }
}