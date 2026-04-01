package com.example.reviewmnl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.ui.theme.BluePrimary

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
            .padding(top = 16.dp, bottom = 24.dp)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "review.mnl",
                    color = contentColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
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
