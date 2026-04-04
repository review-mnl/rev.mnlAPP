package com.example.reviewmnl.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.reviewmnl.R
import com.example.reviewmnl.ui.theme.BluePrimary
import com.example.reviewmnl.ui.theme.MnlBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: com.example.reviewmnl.ui.User?,
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToContact: () -> Unit,
    isLoggedIn: Boolean,
    onLogout: () -> Unit,
    onUpdateUser: (com.example.reviewmnl.ui.User) -> Unit
) {
    val context = LocalContext.current
    var isEditing by remember { mutableStateOf(false) }
    
    // Form States
    var fullName by remember { mutableStateOf(user?.name ?: "") }
    var emailAddress by remember { mutableStateOf(user?.email ?: "") }
    var bio by remember { mutableStateOf(user?.role ?: "Student | review.mnl member") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(user?.profilePicUri?.let { Uri.parse(it) }) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        // compute responsive sizes
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val heroHeight = (screenHeight * 0.33f).let { if (it < 260.dp) 260.dp else it }

        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                // Hero image area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heroHeight)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, BluePrimary.copy(alpha = 0.35f), BluePrimary.copy(alpha = 0.95f)),
                                    startY = 120f
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        // Top Nav
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LogoText(
                                fontSize = 18.sp,
                                modifier = Modifier.clickable { onNavigateToHome() }
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                listOf("HOME", "CONTACT", "SEARCH").forEach { link ->
                                    Text(
                                        text = link,
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .padding(horizontal = 6.dp)
                                            .clickable {
                                                when (link) {
                                                    "HOME" -> onNavigateToHome()
                                                    "SEARCH" -> onNavigateToSearch()
                                                    "CONTACT" -> onNavigateToContact()
                                                }
                                            }
                                    )
                                }
                                IconButton(
                                    onClick = { if (isLoggedIn) onLogout() else onNavigateToLogin() },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isLoggedIn) Icons.AutoMirrored.Filled.Logout else Icons.Default.AccountCircle,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (isEditing) "Edit Your Profile" else "Welcome, ${user?.name ?: "Guest"}!",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Profile Circle at the bottom of hero background (Student)
                    Surface(
                        modifier = Modifier
                            .size(90.dp)
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp),
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 6.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (selectedImageUri != null) {
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null,
                                    tint = BluePrimary,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                    }
                }

                // Profile Info or Edit Form
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F7F9)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (!isEditing) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = user?.email ?: "-", color = Color.Gray, fontSize = 13.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = buildAnnotatedString {
                                            val roleText = user?.role ?: "Student | review.mnl member"
                                            if (roleText.contains("review.mnl")) {
                                                val parts = roleText.split("review.mnl")
                                                append(parts[0])
                                                append("review")
                                                withStyle(style = SpanStyle(color = MnlBlue, fontWeight = FontWeight.Bold)) {
                                                    append(".mnl")
                                                }
                                                if (parts.size > 1) append(parts[1])
                                            } else {
                                                append(roleText)
                                            }
                                        },
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    )
                                }
                                OutlinedButton(
                                    onClick = { isEditing = true },
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(1.dp, BluePrimary),
                                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                                ) {
                                    Text("Edit Profile", color = BluePrimary, fontSize = 12.sp)
                                }
                            }
                        } else {
                            // Edit Mode Form
                            Text("Full Name", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                            OutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                            )

                            Text("Email Address", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                            OutlinedTextField(
                                value = emailAddress,
                                onValueChange = { emailAddress = it },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                            )

                            Text("Bio / Short Info", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                            OutlinedTextField(
                                value = bio,
                                onValueChange = { bio = it },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                            )

                            Text("Profile Photo", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)) {
                                Button(
                                    onClick = { launcher.launch("image/*") },
                                    shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0), contentColor = Color.Black),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("Choose File", fontSize = 11.sp)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (selectedImageUri != null) "Photo selected" else "No file chosen",
                                    fontSize = 11.sp,
                                    color = if (selectedImageUri != null) BluePrimary else Color.Gray
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { 
                                        if (fullName.isNotBlank() && emailAddress.isNotBlank()) {
                                            onUpdateUser(com.example.reviewmnl.ui.User(
                                                name = fullName, 
                                                email = emailAddress, 
                                                role = bio,
                                                profilePicUri = selectedImageUri?.toString()
                                            ))
                                            isEditing = false
                                            Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text("Save", fontWeight = FontWeight.Bold)
                                }
                                Button(
                                    onClick = { 
                                        // Reset fields and exit edit mode
                                        fullName = user?.name ?: ""
                                        emailAddress = user?.email ?: ""
                                        bio = user?.role ?: ""
                                        selectedImageUri = user?.profilePicUri?.let { Uri.parse(it) }
                                        isEditing = false 
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text("Cancel", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Rest of the UI remains only when not editing for cleaner look
        if (!isEditing) {
            Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("My Reviews", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    repeat(2) {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                        ) {
                            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFEEEEEE)))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("Name of Review Center", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("Lorem ipsum dolor sit amet", color = Color.Gray, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F7F9))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Saved Centers", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No saved centers yet.", color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(modifier = Modifier.padding(start = 24.dp), text = "Enrolled Review Centers", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(listOf(1, 2, 3, 4)) { _ ->
                    Card(
                        modifier = Modifier.size(150.dp, 120.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = BluePrimary)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Box(modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.White))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Review Center Name", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Program Enrolled", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.fillMaxWidth().height(20.dp).background(BluePrimary))
        SimpleFooter(onNavigateToHome = onNavigateToHome, onNavigateToContact = onNavigateToSearch, onNavigateToSearch = onNavigateToSearch)
    }
}
