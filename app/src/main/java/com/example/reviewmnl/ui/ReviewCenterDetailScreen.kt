package com.example.reviewmnl.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.R
import com.example.reviewmnl.ui.theme.BluePrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReviewCenterDetailScreen(
    centerName: String,
    isLoggedIn: Boolean,
    currentUser: com.example.reviewmnl.ui.User?,
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToContact: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToMessages: () -> Unit
) {
    val center = reviewCenters.find { it.name == centerName } ?: reviewCenters[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
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
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, BluePrimary.copy(alpha = 0.5f), BluePrimary),
                            startY = 350f
                        )
                    )
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
                    Text(
                        text = "review.mnl",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavigateToHome() }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val headerLinks = buildList {
                            add("HOME")
                            add("CONTACT")
                            add("SEARCH")
                            if (isLoggedIn) add("MESSAGES")
                        }
                        headerLinks.forEach { link ->
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
                                            "CONTACT" -> onNavigateToContact()
                                            "SEARCH" -> onNavigateToSearch()
                                            "MESSAGES" -> onNavigateToMessages()
                                        }
                                    }
                            )
                        }

                        var showAccountMenu by remember { mutableStateOf(false) }
                        IconButton(
                            onClick = { if (isLoggedIn) showAccountMenu = true else onNavigateToProfile() },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        DropdownMenu(expanded = showAccountMenu, onDismissRequest = { showAccountMenu = false }) {
                            DropdownMenuItem(text = { Text("My Profile") }, onClick = {
                                showAccountMenu = false
                                onNavigateToProfile()
                            })
                            DropdownMenuItem(text = { Text("Logout") }, onClick = {
                                showAccountMenu = false
                                onLogout()
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Back Button
                Surface(
                    onClick = onBack,
                    color = Color.White,
                    contentColor = BluePrimary,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(32.dp).width(80.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Back", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Center Info Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                            .border(2.dp, Color.White, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.School, 
                            contentDescription = null, 
                            tint = Color.White, 
                            modifier = Modifier.size(36.dp).align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = center.name, 
                            color = Color.White, 
                            fontSize = 22.sp, 
                            fontWeight = FontWeight.Bold,
                            lineHeight = 26.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = center.location, color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                            StarRating(rating = center.rating, starSize = 14.dp, filledColor = Color(0xFFFFB400), emptyColor = Color.White.copy(alpha = 0.4f))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "${center.rating} (10 Reviews)", color = Color.White, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Content Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BluePrimary)
                .padding(20.dp)
        ) {
            // About Section
            Text("About", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = center.about,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 13.sp,
                lineHeight = 20.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                // Left Side: Programs
                Column(modifier = Modifier.weight(1f)) {
                    Text("Programs Offered", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        center.programs.forEach {
                            Surface(
                                color = Color.White.copy(alpha = 0.1f),
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text(
                                    text = it,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { onNavigateToMessages() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = BluePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Schedule a Review", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Right Side: Achievements Card (now more compact)
                Card(
                    modifier = Modifier.width(140.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Awards", color = BluePrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))
                        center.achievements.take(4).forEach { achievement ->
                            Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(vertical = 4.dp)) {
                                Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color(0xFFFFB400), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    achievement, 
                                    color = BluePrimary, 
                                    fontSize = 9.sp, 
                                    lineHeight = 11.sp,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Footer Section
        // Blue separator to visually separate main content from the footer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(BluePrimary)
        )
        SimpleFooter(
            onNavigateToHome = onNavigateToHome,
            onNavigateToContact = onNavigateToContact,
            onNavigateToSearch = onNavigateToSearch
        )
    }
}
