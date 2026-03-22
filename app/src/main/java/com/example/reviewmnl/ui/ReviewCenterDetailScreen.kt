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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.R
import com.example.reviewmnl.ui.theme.BluePrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReviewCenterDetailScreen(
    centerName: String,
    isLoggedIn: Boolean,
    onBack: () -> Unit,
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
                .height(350.dp)
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
                            startY = 400f
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
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(28.dp).clip(CircleShape).background(Color.White).align(Alignment.CenterStart))
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf("HOME", "CONTACT", "SEARCH", "MESSAGES").forEach { link ->
                            Text(
                                text = link, 
                                color = Color.White, 
                                fontSize = 10.sp, 
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    if (link == "MESSAGES") onNavigateToMessages()
                                }
                            )
                        }
                    }
                    IconButton(
                        onClick = { if (isLoggedIn) onLogout() },
                        modifier = Modifier.size(36.dp).align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = if (isLoggedIn) Icons.AutoMirrored.Filled.Logout else Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Back Button
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = BluePrimary),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Back", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.weight(1f))

                // Center Info Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .border(4.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = center.name, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text(text = center.description, color = Color.White, fontSize = 14.sp)
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                            StarRating(rating = center.rating, starSize = 18.dp, filledColor = Color(0xFFFFB400), emptyColor = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "${center.rating} (10)", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Content Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BluePrimary)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(0.6f)) {
                Text("About", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = center.about,
                    color = Color.White,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text("Programs Offered", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    center.programs.forEach {
                        Surface(
                            color = Color.Transparent,
                            border = BorderStroke(1.dp, Color.White),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = it,
                                color = Color.White,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { onNavigateToMessages() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = BluePrimary),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Schedule a review", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Achievements Card
            Card(
                modifier = Modifier.weight(0.4f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Achievements", color = BluePrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    center.achievements.forEach { achievement ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color(0xFFFFB400), modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(achievement, color = BluePrimary, fontSize = 8.sp, lineHeight = 10.sp)
                        }
                    }
                }
            }
        }

        // Footer Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .navigationBarsPadding()
        ) {
            HorizontalDivider(color = Color.White.copy(alpha = 0.3f), thickness = 0.5.dp)
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Logo", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Manila, Philippines", color = Color.White.copy(alpha = 0.7f), fontSize = 8.sp)
                    }
                    Row {
                        listOf(Icons.Default.Facebook, Icons.Default.Language, Icons.Default.PlayCircle, Icons.Default.Email).forEach { icon ->
                            Icon(
                                imageVector = icon, 
                                contentDescription = null, 
                                tint = Color.White, 
                                modifier = Modifier.padding(horizontal = 4.dp).size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
