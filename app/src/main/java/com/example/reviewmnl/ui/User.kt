package com.example.reviewmnl.ui

enum class UserType {
    STUDENT,
    ADMIN
}

data class User(
    val name: String,
    val email: String,
    val role: String = "Student | review.mnl member",
    val profilePicUri: String? = null,
    val userType: UserType = UserType.STUDENT,
    val token: String? = null
)
