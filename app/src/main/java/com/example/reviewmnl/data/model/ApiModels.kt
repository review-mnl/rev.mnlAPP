package com.example.reviewmnl.data.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

// ─── Auth ─────────────────────────────────────────────────────────────────────

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterStudentRequest(
    val fullname: String,
    val email: String,
    val password: String
)

data class ApiUserDto(
    val id: Int,
    val name: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val email: String,
    val role: String, // "student", "review_center", or "admin"
    val bio: String?,
    @SerializedName("profile_picture_url") val profilePictureUrl: String?,
    @SerializedName("logo_url") val logoUrl: String?,
    @SerializedName("business_name") val businessName: String?
)

data class LoginResponse(
    val message: String,
    val token: String,
    val user: ApiUserDto
)

data class ApiMessageResponse(
    val message: String
)

// ─── Centers ──────────────────────────────────────────────────────────────────

data class ReviewCenterDto(
    val id: Int,
    @SerializedName("business_name") val businessName: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    @SerializedName("logo_url") val logoUrl: String?,
    val description: String?,
    val programs: JsonElement?,
    @SerializedName("avg_rating") val avgRating: Double,
    @SerializedName("review_count") val reviewCount: Int
)

data class TestimonialDto(
    val id: Int,
    val content: String,
    val rating: Double,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)

data class ReviewCenterDetailDto(
    val id: Int,
    @SerializedName("business_name") val businessName: String,
    val email: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    @SerializedName("logo_url") val logoUrl: String?,
    val description: String?,
    val programs: JsonElement?,
    val achievements: JsonElement?,
    val schedule: JsonElement?,
    @SerializedName("avg_rating") val avgRating: Double,
    @SerializedName("review_count") val reviewCount: Int,
    val testimonials: List<TestimonialDto>?,
    @SerializedName("isEnrolled") val isEnrolled: Boolean?
)

// ─── Messages ─────────────────────────────────────────────────────────────────

data class ConversationDto(
    @SerializedName("other_user_id") val otherUserId: Int,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("center_id") val centerId: Int,
    @SerializedName("enrollment_id") val enrollmentId: Int?,
    @SerializedName("last_message") val lastMessage: String,
    @SerializedName("last_timestamp") val lastTimestamp: String?,
    @SerializedName("unread_count") val unreadCount: Int,
    @SerializedName("other_role") val otherRole: String,
    @SerializedName("other_name") val otherName: String
)

data class ConversationsResponse(
    val conversations: List<ConversationDto>
)

data class ThreadMessageDto(
    @SerializedName("message_id") val messageId: Int,
    @SerializedName("sender_id") val senderId: Int,
    @SerializedName("receiver_id") val receiverId: Int,
    val message: String,
    @SerializedName("is_read") val isRead: Boolean,
    @SerializedName("created_at") val createdAt: String?
)

data class ThreadMessagesResponse(
    val messages: List<ThreadMessageDto>
)

data class SendMessageRequest(
    @SerializedName("receiver_id") val receiverId: Int,
    val message: String,
    @SerializedName("enrollment_id") val enrollmentId: Int? = null
)

// ─── Helpers ──────────────────────────────────────────────────────────────────

/**
 * Parses a JsonElement that may be a JSON array of strings or a JSON-encoded string
 * (e.g. "[\"item1\",\"item2\"]") into a List<String>.
 */
fun JsonElement?.toStringList(): List<String> {
    if (this == null || isJsonNull) return emptyList()
    return when {
        isJsonArray -> asJsonArray.mapNotNull { if (it.isJsonPrimitive) it.asString else null }
        isJsonPrimitive -> {
            val raw = asString
            try {
                com.google.gson.Gson().fromJson(raw, Array<String>::class.java)?.toList() ?: emptyList()
            } catch (e: Exception) {
                if (raw.isNotBlank()) listOf(raw) else emptyList()
            }
        }
        else -> emptyList()
    }
}

/**
 * Coroutine-safe extension that wraps a Retrofit [Call] in a suspend function.
 * Cancels the in-flight HTTP request automatically when the calling coroutine is cancelled
 * (e.g. when the host Composable leaves the composition).
 *
 * Throws an [Exception] if the response is unsuccessful or the body is null.
 */
suspend fun <T> retrofit2.Call<T>.awaitResult(): T =
    kotlinx.coroutines.suspendCancellableCoroutine { cont ->
        cont.invokeOnCancellation { cancel() }
        enqueue(object : retrofit2.Callback<T> {
            override fun onResponse(
                call: retrofit2.Call<T>,
                response: retrofit2.Response<T>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    cont.resume(body, null)
                } else {
                    cont.resumeWith(
                        Result.failure(Exception("API error ${response.code()}"))
                    )
                }
            }

            override fun onFailure(call: retrofit2.Call<T>, t: Throwable) {
                cont.resumeWith(Result.failure(t))
            }
        })
    }
