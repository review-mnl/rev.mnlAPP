package com.example.reviewmnl.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.R
import com.example.reviewmnl.ui.theme.BluePrimary
import com.example.reviewmnl.ui.theme.BorderColor
import kotlin.math.roundToInt

// Data models
data class Category(val name: String, val icon: ImageVector)
data class ReviewCenter(val name: String, val category: String, val rating: Double, val location: String, val description: String)

// Shared data
val categories = listOf(
    Category("Civil Engineering", Icons.Default.Engineering),
    Category("Mechanical Engineering", Icons.Default.Settings),
    Category("Electrical Engineering", Icons.Default.Bolt),
    Category("Electronics Engineering", Icons.Default.Memory),
    Category("Chemical Engineering", Icons.Default.Science),
    Category("Architecture", Icons.Default.Architecture),
    Category("Law", Icons.Default.Gavel),
    Category("Medicine", Icons.Default.MedicalServices),
    Category("Nursing", Icons.Default.Vaccines),
    Category("Pharmacy", Icons.Default.Medication)
)

val reviewCenters = listOf(
    ReviewCenter("FastTrack Civil Review", "Civil Engineering", 4.8, "Manila, Philippines", "The best review center for aspiring Civil Engineers."),
    ReviewCenter("Justice Law Academy", "Law", 4.9, "Makati, Philippines", "Excellence in legal education and bar exam preparation."),
    ReviewCenter("MedLife Review Center", "Medicine", 4.6, "Davao City, Philippines", "Your partner in achieving your medical dreams."),
    ReviewCenter("BuildSmart Architecture", "Architecture", 4.5, "Taguig, Philippines", "Designing your path to becoming a licensed Architect.")
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    isLoggedIn: Boolean,
    onNavigateToLogin: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToSearch: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 220.dp, max = 280.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, BluePrimary.copy(alpha = 0.3f), BluePrimary),
                            startY = 300f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Top Nav
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .align(Alignment.CenterStart)
                    )
                    
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
                                modifier = Modifier
                                    .clickable { 
                                        if (link == "SEARCH") {
                                            if (isLoggedIn) onNavigateToSearch() else Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            )
                        }
                    }

                    IconButton(
                        onClick = { if (isLoggedIn) onLogout() else onNavigateToLogin() },
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = if (isLoggedIn) Icons.AutoMirrored.Filled.Logout else Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Search Bar Trigger
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable { if (isLoggedIn) onNavigateToSearch() else Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show() },
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Search review centers...", 
                            color = Color.Gray, 
                            fontSize = 14.sp, 
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Blue Middle Section (Programs)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BluePrimary)
                .padding(vertical = 16.dp)
        ) {
            FlowRow(
                modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                maxItemsInEachRow = 4
            ) {
                categories.forEach { category ->
                    FilterPill(
                        label = category.name,
                        isSelected = false,
                        onClick = { 
                            if (isLoggedIn) onNavigateToSearch() 
                            else Toast.makeText(context, "Please login to filter", Toast.LENGTH_SHORT).show() 
                        }
                    )
                }
            }
        }

        // Featured Section
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(0.45f)) {
                    Text(
                        "Featured", 
                        color = BluePrimary, 
                        fontSize = 24.sp, 
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "Top rated review centers based on student feedback.", 
                        fontSize = 9.sp, 
                        color = Color.Gray,
                        lineHeight = 12.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = BluePrimary, 
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.clickable { /* action */ }
                    ) {
                        Text(
                            "VIEW ALL", 
                            color = Color.White, 
                            fontSize = 7.sp, 
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Box(modifier = Modifier.weight(0.55f)) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(end = 24.dp)
                    ) {
                        items(reviewCenters) { center -> FeaturedCard(center) }
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowCircleRight, 
                        contentDescription = null, 
                        tint = BluePrimary, 
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .background(Color.White.copy(alpha = 0.8f), CircleShape)
                    )
                }
            }
        }

        // Footer Section
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            HorizontalDivider(color = BluePrimary, thickness = 6.dp)
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(), 
                    horizontalArrangement = Arrangement.SpaceBetween, 
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Logo", color = BluePrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Manila, Philippines", color = Color.Gray, fontSize = 9.sp)
                    }
                    Row {
                        listOf(Icons.Default.Facebook, Icons.Default.Language, Icons.Default.PlayCircle, Icons.Default.Email).forEach { icon ->
                            Icon(icon, contentDescription = null, tint = BluePrimary, modifier = Modifier.padding(horizontal = 3.dp).size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterPill(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .clickable { onClick() },
        color = if (isSelected) Color.White else Color.Transparent,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .border(1.dp, if (isSelected) BluePrimary else Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label, 
                color = if (isSelected) BluePrimary else Color.White, 
                fontSize = 8.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun FeaturedCard(center: ReviewCenter) {
    Card(
        modifier = Modifier.width(130.dp).height(160.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BluePrimary)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(modifier = Modifier.fillMaxWidth().height(70.dp).clip(RoundedCornerShape(8.dp)).background(Color.White))
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                center.name, 
                color = Color.White, 
                fontSize = 10.sp, 
                fontWeight = FontWeight.Bold, 
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                center.category, 
                color = Color.White.copy(alpha = 0.7f), 
                fontSize = 8.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val ratingInt = center.rating.roundToInt()
                repeat(5) { i ->
                    Icon(
                        Icons.Default.Star, 
                        null, 
                        tint = if (i < ratingInt) Color(0xFFFFB400) else Color.White, 
                        modifier = Modifier.size(10.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(Color.White))
            }
        }
    }
}

@Composable
fun LargeReviewCenterCard(center: ReviewCenter, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(50.dp).clip(RoundedCornerShape(6.dp)).background(BluePrimary))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = center.name, 
                    fontWeight = FontWeight.Bold, 
                    fontSize = 14.sp, 
                    color = BluePrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = center.location, 
                    color = Color.Gray, 
                    fontSize = 9.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val ratingInt = center.rating.roundToInt()
                    repeat(5) { i ->
                        Icon(
                            imageVector = Icons.Default.Star, 
                            contentDescription = null, 
                            tint = if (i < ratingInt) Color(0xFFFFB400) else Color.LightGray, 
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}
