package com.example.reviewmnl.data.api

import com.example.reviewmnl.data.model.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Retrofit interface mapping to the review.mnl backend (Node.js + MySQL on Railway).
 * Base URL: https://reviewmnl-production-67eb.up.railway.app/
 *
 * In VS Code, the backend routes are in:
 *   review.mnl-backend/review.mnl-backend/routes/
 *   review.mnl-backend/review.mnl-backend/controllers/
 */
interface ApiService {

    // ─── Authentication  (routes/auth.js) ────────────────────────────────────

    /** POST /api/auth/login  →  { token, user } */
    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    /** POST /api/auth/register/student  →  { message } */
    @POST("api/auth/register/student")
    fun registerStudent(@Body request: RegisterStudentRequest): Call<ApiMessageResponse>

    /** POST /api/auth/forgot-password  →  { message } */
    @POST("api/auth/forgot-password")
    fun forgotPassword(@Body body: Map<String, String>): Call<ApiMessageResponse>

    /** POST /api/auth/resend-verification  →  { message } */
    @POST("api/auth/resend-verification")
    fun resendVerification(@Body body: Map<String, String>): Call<ApiMessageResponse>

    // ─── Review Centers  (routes/centers.js) ─────────────────────────────────

    /** GET /api/centers  →  List of approved centers */
    @GET("api/centers")
    fun getCenters(): Call<List<ReviewCenterDto>>

    /** GET /api/centers/search?q=query  →  Filtered list of centers */
    @GET("api/centers/search")
    fun searchCenters(@Query("q") query: String): Call<List<ReviewCenterDto>>

    /** GET /api/centers/{id}  →  Full center detail with testimonials */
    @GET("api/centers/{id}")
    fun getCenterById(@Path("id") id: Int): Call<ReviewCenterDetailDto>

    // ─── Users  (routes/users.js) ─────────────────────────────────────────────

    /** GET /api/users/me  →  Current authenticated user's profile */
    @GET("api/users/me")
    fun getMyProfile(): Call<ApiUserDto>

    /** PUT /api/users/me  →  Update profile fields */
    @PUT("api/users/me")
    fun updateMyProfile(@Body body: Map<String, String>): Call<ApiUserDto>

    // ─── Messages  (routes/messages.js) ──────────────────────────────────────

    /** GET /api/messages/conversations  →  { conversations: [...] } */
    @GET("api/messages/conversations")
    fun getConversations(): Call<ConversationsResponse>

    /** GET /api/messages/thread?withUserId=X  →  { messages: [...] } */
    @GET("api/messages/thread")
    fun getThreadMessages(@Query("withUserId") withUserId: Int): Call<ThreadMessagesResponse>

    /** POST /api/messages  →  Send a message */
    @POST("api/messages")
    fun sendMessage(@Body request: SendMessageRequest): Call<ApiMessageResponse>

    /** PUT /api/messages/thread/read  →  Mark thread as read */
    @PUT("api/messages/thread/read")
    fun markThreadAsRead(@Body body: Map<String, Int>): Call<ApiMessageResponse>
}
