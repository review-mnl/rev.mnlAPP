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
import androidx.compose.material.icons.automirrored.filled.StarHalf
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.R
import com.example.reviewmnl.ui.theme.BluePrimary
import com.example.reviewmnl.ui.theme.BorderColor
import kotlin.math.floor
import kotlin.math.roundToInt

// Data models
data class Category(val name: String, val icon: ImageVector)
data class ReviewCenter(
    val name: String, 
    val category: String, 
    val rating: Double, 
    val location: String, 
    val description: String,
    val about: String,
    val achievements: List<String>,
    val programs: List<String>
)

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
    ReviewCenter(
        name = "Gillesania Engineering Review Center",
        category = "Civil Engineering",
        rating = 4.9,
        location = "Cebu City, Philippines",
        description = "A premier review center specializing in Civil Engineering board exam preparations with high passing rates.",
        about = "Gillesania Engineering Review Center is one of the most respected names in Engineering licensure exam preparation in the Philippines. Founded by Engr. Diego Inocencio T. Gillesania, the center has consistently produced topnotchers and thousands of licensed Civil Engineers over the decades. Our teaching method focuses on mastery of fundamentals and innovative problem-solving techniques.",
        achievements = listOf(
            "Produced over 500 Topnotchers since founding",
            "Highest Passing Rate in CE Board Exams 2023",
            "Awarded Excellence in Technical Education",
            "Consistent Top 1 Performance in various board years",
            "Most Trusted Engineering Review Center in Visayas",
            "Innovative Online Review Platform Pioneer"
        ),
        programs = listOf("Civil Engineering Board", "Master Plumber Review", "Surveying Specialist", "Structural Design")
    ),
    ReviewCenter(
        name = "Jurists Bar Review Center",
        category = "Law",
        rating = 4.8,
        location = "Manila, Philippines",
        description = "Provides comprehensive and innovative bar review programs with a focus on mock bar exams and coaching.",
        about = "Jurists Bar Review Center is renowned for its 'Jurists Method' of law review, which emphasizes logic-based answering and rigorous mock bar examinations. We provide personalized coaching from seasoned legal professionals and former bar examiners. Our mission is to transform law graduates into licensed attorneys through strategic preparation and mental conditioning.",
        achievements = listOf(
            "Consistent high passing rate in Philippine Bar Exams",
            "Pioneered the One-on-One Coaching Program",
            "Recognized for Logic-based Answering Techniques",
            "Produced several Top 10 Bar Topnotchers",
            "Leading Online Bar Review Provider",
            "Comprehensive Mock Bar Examination Series"
        ),
        programs = listOf("Pre-Bar Review", "Pre-Week Review", "Mock Bar Series", "Special Law Lectures")
    ),
    ReviewCenter(
        name = "Toprank Review Academy",
        category = "Nursing",
        rating = 4.7,
        location = "Sampaloc, Manila",
        description = "The leading review academy for Nursing and Healthcare professionals, known for its strategic test-taking techniques.",
        about = "Toprank Review Academy is a multi-awarded institution dedicated to empowering healthcare professionals. We specialize in the Philippine Nursing Licensure Exam (PNLE) and international certifications. Our approach combines intensive lectures, cognitive-based test strategies, and digital learning tools to ensure student success in a global healthcare landscape.",
        achievements = listOf(
            "Ranked #1 Nursing Review Center in the Philippines",
            "100% Passing Rate for several school batches",
            "Top 1 PNLE Performance for consecutive years",
            "Global Excellence Award in Healthcare Education",
            "Pioneered AI-Driven Review Analytics",
            "Largest network of Nursing Reviewees nationwide"
        ),
        programs = listOf("Nursing Board (PNLE)", "NCLEX-RN Review", "HAAD/DHA/MOH Review", "Prometric Review")
    ),
    ReviewCenter(
        name = "JPT Review Center",
        category = "Architecture",
        rating = 4.6,
        location = "Quezon City, Philippines",
        description = "Dedicated to molding future Architects through intensive design and theory-focused review sessions.",
        about = "JPT Review Center (Joint Professional Training) is a specialized hub for Architecture and Environmental Planning licensure. We provide a deep dive into Architectural Design, History, and Building Laws. Our mentors are practicing architects who bring real-world insights into the classroom, preparing students not just for the board, but for professional practice.",
        achievements = listOf(
            "Top-performing Architecture Review Center",
            "Specialist in Design and Site Planning",
            "Produced multiple Board Topnotchers",
            "Comprehensive Building Laws Seminar Series",
            "Innovative Digital Graphics Review",
            "High success rate in Environmental Planning Board"
        ),
        programs = listOf("Architecture Board (ALE)", "Environmental Planning Board", "Master Plumber", "Design Mock Exams")
    ),
    ReviewCenter(
        name = "Excel Review Center",
        category = "Electronics Engineering",
        rating = 4.7,
        location = "Manila, Philippines",
        description = "Specializes in ECE and ECT board exam reviews with state-of-the-art facilities and experienced mentors.",
        about = "Excel Review Center is a powerhouse in Electronics and Electrical Engineering reviews. With a legacy spanning over 30 years, we provide students with the most updated materials and laboratory-based review experiences. Our curriculum is constantly updated to reflect the evolving trends in the ECE Board Examinations.",
        achievements = listOf(
            "Decades of consistent high passing rates",
            "Modern laboratory facilities for technical review",
            "Extensive library of ECE Board exam patterns",
            "Renowned mentors in Electronics and Math",
            "First ECE Review Center with Mobile App support",
            "Consistent producer of ECE and ECT Topnotchers"
        ),
        programs = listOf("ECE Board Review", "ECT Board Review", "Electrical Engineering Board", "Technical Workshops")
    )
)

@Composable
fun StarRating(rating: Double, starSize: Dp = 14.dp, filledColor: Color = Color(0xFFFFB400), emptyColor: Color = Color.LightGray) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val fullStars = floor(rating).toInt()
        val hasHalfStar = rating - fullStars >= 0.1 // Using 0.1 threshold for "partial"

        repeat(5) { i ->
            val icon = when {
                i < fullStars -> Icons.Default.Star
                i == fullStars && hasHalfStar -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Default.Star
            }
            val tint = when {
                i < fullStars -> filledColor
                i == fullStars && hasHalfStar -> filledColor
                else -> emptyColor
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(starSize)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    isLoggedIn: Boolean,
    onNavigateToLogin: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToMessages: () -> Unit,
    onNavigateToContact: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
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
                        listOf("HOME", "CONTACT", "SEARCH", "MESSAGES").forEach { link ->
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
                                        when (link) {
                                            "SEARCH" -> if (isLoggedIn) onNavigateToSearch() else Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show()
                                            "MESSAGES" -> onNavigateToMessages()
                                            "CONTACT" -> onNavigateToContact()
                                            "HOME" -> onNavigateToHome()
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
                        modifier = Modifier.clickable { 
                            if (isLoggedIn) onNavigateToSearch() 
                            else Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show()
                        }
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
                        items(reviewCenters) { center -> 
                            FeaturedCard(
                                center = center,
                                onClick = {
                                    if (isLoggedIn) onNavigateToDetail(center.name)
                                    else Toast.makeText(context, "Please login to view details", Toast.LENGTH_SHORT).show()
                                }
                            ) 
                        }
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
        SimpleFooter(
            onNavigateToHome = onNavigateToHome,
            onNavigateToContact = onNavigateToContact,
            onNavigateToSearch = onNavigateToSearch
        )
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
fun FeaturedCard(center: ReviewCenter, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(160.dp)
            .clickable { onClick() },
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
                StarRating(rating = center.rating, starSize = 10.dp, filledColor = Color(0xFFFFB400), emptyColor = Color.White)
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
                StarRating(rating = center.rating, starSize = 12.dp)
            }
        }
    }
}
