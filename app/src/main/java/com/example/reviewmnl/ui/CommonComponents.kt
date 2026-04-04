package com.example.reviewmnl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.ui.theme.BluePrimary
import com.example.reviewmnl.ui.theme.MnlBlue

@Composable
fun LogoText(
    modifier: Modifier = Modifier,
    fontSize: androidx.compose.ui.unit.TextUnit = 18.sp,
    baseColor: Color = Color.White
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = baseColor)) {
                append("review")
            }
            withStyle(style = SpanStyle(color = MnlBlue)) { // Soft blue for .mnl
                append(".mnl")
            }
        },
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun SimpleFooter(
    onNavigateToHome: () -> Unit,
    onNavigateToContact: () -> Unit,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    contentColor: Color = BluePrimary
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .navigationBarsPadding()
    ) {
        // Blue separator bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(BluePrimary)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                LogoText(
                    baseColor = contentColor,
                    fontSize = 18.sp,
                    modifier = Modifier.clickable { onNavigateToHome() }
                )
                Text("Manila, Philippines", color = if (backgroundColor == Color.White) Color.Gray else Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    "Home", 
                    color = if (backgroundColor == Color.White) Color.Gray else Color.White, 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onNavigateToHome() }
                )
                Text(
                    "Contact", 
                    color = if (backgroundColor == Color.White) Color.Gray else Color.White, 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onNavigateToContact() }
                )
                Text(
                    "Search", 
                    color = if (backgroundColor == Color.White) Color.Gray else Color.White,
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onNavigateToSearch() }
                )
            }
        }
    }
}

@Composable
fun AdminFooter(
    onTabSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    contentColor: Color = BluePrimary
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .navigationBarsPadding()
    ) {
        // Blue separator bar - copied from student dashboard look
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(BluePrimary)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                LogoText(
                    baseColor = contentColor,
                    fontSize = 18.sp,
                    modifier = Modifier.clickable { onTabSelect("DASHBOARD") }
                )
                Text("Manila, Philippines", color = if (backgroundColor == Color.White) Color.Gray else Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    "Dashboard", 
                    color = if (backgroundColor == Color.White) Color.Gray else Color.White, 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onTabSelect("DASHBOARD") }
                )
                Text(
                    "Students", 
                    color = if (backgroundColor == Color.White) Color.Gray else Color.White, 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onTabSelect("MANAGE_STUDENTS") }
                )
                Text(
                    "Profile", 
                    color = if (backgroundColor == Color.White) Color.Gray else Color.White,
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onTabSelect("MY_PROFILE") }
                )
            }
        }
    }
}
