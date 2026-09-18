package com.roddy.bidflow.user.dto

import jakarta.validation.constraints.NotNull

data class UserRequest(
    @NotNull
    val username: String,
    @NotNull
    val password: String,
    @NotNull
    val email: String
)
