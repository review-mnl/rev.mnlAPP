package com.example.reviewmnl.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.R
import com.example.reviewmnl.ui.theme.BluePrimary
import com.example.reviewmnl.ui.theme.GreyText
import com.example.reviewmnl.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    var isLoginMode by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isStudentSelected by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
            
            Text(
                text = "review.mnl",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
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
                        text = if (isLoginMode) "Welcome back to review.mnl" else "Create an account to get started",
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

                    // Social Login
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("G", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("f", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
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
                            } else {
                                // Logic for simulation
                                onLoginSuccess(email)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (isLoginMode) "LOGIN" else "SIGN UP", fontWeight = FontWeight.Bold)
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
                            }
                    )
                }
            }
        }
    }
}
