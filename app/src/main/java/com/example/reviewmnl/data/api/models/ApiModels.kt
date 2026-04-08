package com.example.reviewmnl.data.api.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserDto?
)

data class UserDto(
    val id: Int,
    val name: String,
    val first_name: String?,
    val last_name: String?,
    val email: String,
    val role: String,
    val bio: String?,
    val profile_picture_url: String?
)

data class MyProfileResponse(
    val email: String,
    val first_name: String?,
    val last_name: String?,
    val bio: String?,
    val profile_picture_url: String?
)

data class UpdateProfileRequest(
    val first_name: String?,
    val last_name: String?,
    val bio: String?
)

data class UpdateCenterProfileRequest(
    val business_name: String?,
    val description: String?,
    val address: String?
)

data class ReviewCenterResponse(
    val id: Int,
    val business_name: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val logo_url: String?,
    val description: String?,
    val programs: String?,
    val avg_rating: Double?,
    val review_count: Int?
)