package com.example.reviewmnl.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.R
import com.example.reviewmnl.ui.theme.BluePrimary
import com.example.reviewmnl.ui.theme.MnlBlue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ContactScreen(
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToLogin: () -> Unit,
    isLoggedIn: Boolean,
    onLogout: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var messageText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    
    val subjects = listOf("General Inquiry", "Technical Support", "Partnership", "Feedback", "Other")

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.heightIn(min = screenHeight)
            ) {
                // Hero Section with Top Nav
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.viewcenterbg),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f))
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                listOf("HOME", "CONTACT", "SEARCH").forEach { link ->
                                    Text(
                                        text = link,
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier
                                            .padding(horizontal = 6.dp)
                                            .clickable {
                                                when(link) {
                                                    "HOME" -> onNavigateToHome()
                                                    "SEARCH" -> onNavigateToSearch()
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

                        Spacer(modifier = Modifier.weight(1f))

                        // Hero Content
                        Text(
                            "Contact Us",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "We'd love to hear from you",
                            color = Color.White,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                // Main Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "Get in Touch",
                        color = BluePrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Have questions, feedback, or need help? Send us a message and we'll get back to you as soon as possible.",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                    )

                    // Info Cards
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ContactCard(
                            icon = Icons.Default.Email,
                            title = "Email Us",
                            content = buildAnnotatedString {
                                append("support@review")
                                withStyle(style = SpanStyle(color = MnlBlue, fontWeight = FontWeight.Bold)) {
                                    append(".mnl")
                                }
                            }.toString(),
                            subContent = "We reply within 24-48 hours"
                        )
                        ContactCard(
                            icon = Icons.Default.LocationOn,
                            title = "Location",
                            content = "Manila, Philippines",
                            subContent = ""
                        )
                        ContactCard(
                            icon = Icons.Default.Schedule,
                            title = "Support Hours",
                            content = "Monday - Friday",
                            subContent = "8:00 AM - 5:00 PM (PHT)"
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Send a Message Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                "Send a Message",
                                color = BluePrimary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = buildAnnotatedString {
                                    append("Fill out the form below and we'll respond as soon as we can to help you with review")
                                    withStyle(style = SpanStyle(color = MnlBlue, fontWeight = FontWeight.Bold)) {
                                        append(".mnl")
                                    }
                                },
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                            )

                            // Form Fields
                            Text("Full Name *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
                            OutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                placeholder = { Text("Your full name", fontSize = 14.sp) },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp),
                                shape = RoundedCornerShape(8.dp)
                            )

                            Text("Email Address *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
                            OutlinedTextField(
                                value = emailAddress,
                                onValueChange = { emailAddress = it },
                                placeholder = { Text("your@email.com", fontSize = 14.sp) },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp),
                                shape = RoundedCornerShape(8.dp)
                            )

                            Text("Subject *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp)
                            ) {
                                OutlinedTextField(
                                    value = subject,
                                    onValueChange = {},
                                    readOnly = true,
                                    placeholder = { Text("Select a subject", fontSize = 14.sp) },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    subjects.forEach { selectionOption ->
                                        DropdownMenuItem(
                                            text = { Text(selectionOption) },
                                            onClick = {
                                                subject = selectionOption
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Text("Message *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
                            OutlinedTextField(
                                value = messageText,
                                onValueChange = { messageText = it },
                                placeholder = { Text("Write your message here... (min. 10 characters)", fontSize = 14.sp) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(top = 4.dp, bottom = 20.dp),
                                shape = RoundedCornerShape(8.dp)
                            )

                            Button(
                                onClick = { /* Action */ },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (fullName.isNotBlank() && emailAddress.isNotBlank()) BluePrimary else Color(0xFFCFD8DC)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Send Message", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(48.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                // Footer Section
                SimpleFooter(
                    onNavigateToHome = onNavigateToHome,
                    onNavigateToContact = { /* Already here */ },
                    onNavigateToSearch = onNavigateToSearch
                )
            }
        }
    }
}

@Composable
fun ContactCard(icon: ImageVector, title: String, content: String, subContent: String, isClickable: Boolean = false) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(36.dp),
                shape = RoundedCornerShape(8.dp),
                color = BluePrimary.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, color = BluePrimary, fontSize = 14.sp)
                
                if (content.contains("review.mnl")) {
                    Text(
                        text = buildAnnotatedString {
                            val parts = content.split(".mnl")
                            append(parts[0])
                            withStyle(style = SpanStyle(color = MnlBlue, fontWeight = FontWeight.Bold)) {
                                append(".mnl")
                            }
                            if (parts.size > 1) append(parts[1])
                        },
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                } else {
                    Text(content, fontSize = 12.sp, color = Color.Black)
                }

                if (subContent.isNotBlank()) {
                    Text(
                        subContent, 
                        fontSize = 11.sp, 
                        color = if (isClickable) BluePrimary else Color.Gray,
                        textDecoration = if (isClickable) androidx.compose.ui.text.style.TextDecoration.Underline else null
                    )
                }
            }
        }
    }
}

@Composable
fun SocialIcon(icon: ImageVector) {
    Surface(
        modifier = Modifier.padding(horizontal = 6.dp).size(32.dp),
        shape = CircleShape,
        color = BluePrimary
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
        }
    }
}
