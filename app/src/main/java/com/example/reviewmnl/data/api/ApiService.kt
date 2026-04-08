package com.example.reviewmnl.data.api

import com.example.reviewmnl.data.api.models.LoginRequest
import com.example.reviewmnl.data.api.models.LoginResponse
import com.example.reviewmnl.data.api.models.MyProfileResponse
import com.example.reviewmnl.data.api.models.ReviewCenterResponse
import com.example.reviewmnl.data.api.models.UpdateProfileRequest
import com.example.reviewmnl.data.api.models.UpdateCenterProfileRequest
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Response

interface ApiService {
    
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/centers")
    suspend fun getApprovedCenters(): Response<List<ReviewCenterResponse>>

    @GET("api/users/me")
    suspend fun getStudentProfile(@Header("Authorization") token: String): Response<MyProfileResponse>

    @PUT("api/users/me")
    suspend fun updateStudentProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): Response<Void>

    @GET("api/centers/me")
    suspend fun getCenterProfile(@Header("Authorization") token: String): Response<ReviewCenterResponse>

    @PUT("api/centers/me")
    suspend fun updateCenterProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateCenterProfileRequest
    ): Response<Void>
}