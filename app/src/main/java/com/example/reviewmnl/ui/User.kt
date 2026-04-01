package com.example.reviewmnl.ui

data class User(
    val name: String,
    val email: String,
    val role: String = "Student | review.mnl member",
    val profilePicUri: String? = null
)
