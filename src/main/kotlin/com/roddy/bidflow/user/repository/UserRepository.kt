package com.roddy.bidflow.user.repository

import com.roddy.bidflow.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {

    fun findByUsername(username: String): User?
}
