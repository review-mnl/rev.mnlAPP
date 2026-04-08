package com.example.reviewmnl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.data.api.RetrofitClient
import com.example.reviewmnl.data.local.TokenManager
import com.example.reviewmnl.data.model.ApiMessageResponse
import com.example.reviewmnl.data.model.LoginRequest
import com.example.reviewmnl.data.model.LoginResponse
import com.example.reviewmnl.data.model.RegisterStudentRequest
import com.example.reviewmnl.ui.theme.BluePrimary
import com.example.reviewmnl.ui.theme.GreyText
import com.example.reviewmnl.ui.theme.LightBlue
import com.example.reviewmnl.ui.theme.MnlBlue
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onLoginSuccess: (String, Boolean) -> Unit
) {
    var isLoginMode by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isStudentSelected by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BluePrimary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.offset(x = (-12).dp) // Adjust for icon padding
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            
            LogoText(
                fontSize = 28.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (isLoginMode) "Login" else "Sign Up",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = BluePrimary
                    )
                    
                    Text(
                        text = buildAnnotatedString {
                            append(if (isLoginMode) "Welcome back to review" else "Create an account to get started with review")
                            withStyle(style = SpanStyle(color = MnlBlue, fontWeight = FontWeight.Bold)) {
                                append(".mnl")
                            }
                        },
                        fontSize = 14.sp,
                        color = GreyText,
                        modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                    )

                    // Error Message
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Success Message (e.g. after registration)
                    if (successMessage != null) {
                        Text(
                            text = successMessage!!,
                            color = Color(0xFF2E7D32),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Student / Review Center Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(LightBlue, RoundedCornerShape(24.dp))
                            .padding(4.dp)
                    ) {
                        Button(
                            onClick = { isStudentSelected = true },
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isStudentSelected) BluePrimary else Color.Transparent,
                                contentColor = if (isStudentSelected) Color.White else GreyText
                            ),
                            elevation = null,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Student", fontSize = 12.sp)
                        }
                        Button(
                            onClick = { isStudentSelected = false },
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!isStudentSelected) BluePrimary else Color.Transparent,
                                contentColor = if (!isStudentSelected) Color.White else GreyText
                            ),
                            elevation = null,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Review Center", fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Social Login - Only Google
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("G", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        HorizontalDivider(modifier = Modifier.weight(1f))
                        Text(" OR ", fontSize = 12.sp, color = GreyText)
                        HorizontalDivider(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Email Address", fontSize = 12.sp, color = BluePrimary, fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = email,
                        onValueChange = { 
                            email = it
                            errorMessage = null
                            successMessage = null
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        placeholder = { Text("Enter your email", fontSize = 14.sp) },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Password", fontSize = 12.sp, color = BluePrimary, fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = password,
                        onValueChange = { 
                            password = it
                            errorMessage = null
                            successMessage = null
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        placeholder = { Text("Enter your password", fontSize = 14.sp) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    if (!isLoginMode) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Confirm Password", fontSize = 12.sp, color = BluePrimary, fontWeight = FontWeight.Medium)
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { 
                                confirmPassword = it
                                errorMessage = null
                            },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            placeholder = { Text("Confirm your password", fontSize = 14.sp) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                errorMessage = "Please fill in all fields"
                            } else if (!isLoginMode && password != confirmPassword) {
                                errorMessage = "Passwords do not match"
                            } else if (isLoginMode) {
                                performLogin(
                                    email = email.trim(),
                                    password = password,
                                    setLoading = { isLoading = it },
                                    setError = { errorMessage = it },
                                    onSuccess = onLoginSuccess
                                )
                            } else {
                                if (!isStudentSelected) {
                                    // Review Center registration requires documents – guide user to web
                                    errorMessage = "Review center registration requires uploading documents. Please register at https://review-mnl.vercel.app"
                                } else {
                                    performRegisterStudent(
                                        email = email.trim(),
                                        password = password,
                                        setLoading = { isLoading = it },
                                        setError = { errorMessage = it },
                                        onSuccess = {
                                            successMessage = "Account created! Please check your email to verify before logging in."
                                            isLoginMode = true
                                            password = ""
                                            confirmPassword = ""
                                        }
                                    )
                                }
                            }
                        },
                        enabled = !isLoading,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(if (isLoginMode) "LOGIN" else "SIGN UP", fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (isLoginMode) "Don't have an account? Sign Up" else "Already have an account? Login",
                        color = BluePrimary,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .clickable { 
                                isLoginMode = !isLoginMode
                                errorMessage = null
                                successMessage = null
                            }
                    )
                }
            }
        }
    }
}

/** Calls POST /api/auth/login and handles the response. */
private fun performLogin(
    email: String,
    password: String,
    setLoading: (Boolean) -> Unit,
    setError: (String?) -> Unit,
    onSuccess: (String, Boolean) -> Unit
) {
    setLoading(true)
    setError(null)
    RetrofitClient.apiService.login(LoginRequest(email, password))
        .enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                setLoading(false)
                if (response.isSuccessful) {
                    val body = response.body() ?: return
                    TokenManager.token = body.token
                    val isStudent = body.user.role == "student"
                    onSuccess(body.user.email, isStudent)
                } else {
                    setError(parseApiError(response.errorBody()?.string(), "Login failed. Please try again."))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                setLoading(false)
                setError("Network error. Please check your connection.")
            }
        })
}

/** Calls POST /api/auth/register/student and handles the response. */
private fun performRegisterStudent(
    email: String,
    password: String,
    setLoading: (Boolean) -> Unit,
    setError: (String?) -> Unit,
    onSuccess: () -> Unit
) {
    setLoading(true)
    setError(null)
    // Derive a temporary display name from the email local-part (e.g. "john.doe@..." → "John.doe").
    // A dedicated fullname input field can be added to the registration form in a future iteration.
    val namePart = email.substringBefore("@").replaceFirstChar { it.uppercase() }
    RetrofitClient.apiService.registerStudent(
        RegisterStudentRequest(fullname = namePart, email = email, password = password)
    ).enqueue(object : Callback<ApiMessageResponse> {
        override fun onResponse(call: Call<ApiMessageResponse>, response: Response<ApiMessageResponse>) {
            setLoading(false)
            if (response.isSuccessful) {
                onSuccess()
            } else {
                setError(parseApiError(response.errorBody()?.string(), "Registration failed. Please try again."))
            }
        }

        override fun onFailure(call: Call<ApiMessageResponse>, t: Throwable) {
            setLoading(false)
            setError("Network error. Please check your connection.")
        }
    })
}

/** Extracts the `message` field from a JSON error response body, or returns [fallback]. */
private fun parseApiError(errorBody: String?, fallback: String): String {
    if (errorBody.isNullOrBlank()) return fallback
    return try {
        JSONObject(errorBody).optString("message", fallback)
    } catch (e: Exception) {
        fallback
    }
}

