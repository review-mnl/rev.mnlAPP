package com.example.reviewmnl.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.platform.LocalContext
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
import java.util.Calendar

@Composable
fun AdminDashboardScreen(
    user: User?,
    onLogout: () -> Unit,
    onNavigateToMessages: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("DASHBOARD") }
    var showProfileMenu by remember { mutableStateOf(false) }
    
    // Profile states for editing
    var centerName by remember { mutableStateOf("Review Cench") }
    var centerLocation by remember { mutableStateOf("Pasay City") }
    var centerDescription by remember { mutableStateOf("A review center located at Pasay City!") }
    var achievements by remember { mutableStateOf(listOf("15 TOP-NOTCHERS 2025 BOARD EXAM")) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    
    var operatingHours by remember { mutableStateOf(mapOf(
        "Sun" to "Closed",
        "Mon" to "8:00 AM - 6:00 PM",
        "Tue" to "8:00 AM - 6:00 PM",
        "Wed" to "8:00 AM - 6:00 PM",
        "Thu" to "8:00 AM - 6:00 PM",
        "Fri" to "8:00 AM - 6:00 PM",
        "Sat" to "8:00 AM - 6:00 PM"
    )) }

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
            // Main content column that at least fills the screen height
            Column(
                modifier = Modifier.heightIn(min = screenHeight)
            ) {
                // Mobile Hero Section with Nav
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
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
                            .background(Color.Black.copy(alpha = 0.4f))
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
                                modifier = Modifier.clickable { selectedTab = "DASHBOARD" }
                            )
                            
                            Spacer(modifier = Modifier.weight(1f))
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    "MANAGE STUDENTS", 
                                    color = Color.White, 
                                    fontSize = 8.sp, 
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.clickable { selectedTab = "MANAGE_STUDENTS" }
                                )
                                Text(
                                    "DASHBOARD", 
                                    color = Color.White, 
                                    fontSize = 8.sp, 
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.clickable { selectedTab = "DASHBOARD" }
                                )
                                
                                Box {
                                    IconButton(onClick = { showProfileMenu = true }, modifier = Modifier.size(24.dp)) {
                                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = Color.White, modifier = Modifier.size(20.dp))
                                    }
                                    DropdownMenu(
                                        expanded = showProfileMenu,
                                        onDismissRequest = { showProfileMenu = false }
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("My Profile") },
                                            onClick = {
                                                selectedTab = "MY_PROFILE"
                                                showProfileMenu = false
                                            },
                                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Logout") },
                                            onClick = {
                                                showProfileMenu = false
                                                onLogout()
                                            },
                                            leadingIcon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null) }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = when(selectedTab) {
                                "MANAGE_STUDENTS" -> "Manage Students"
                                "MY_PROFILE" -> "Welcome, $centerName!"
                                else -> "Welcome, $centerName!"
                            },
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Profile Circle at the bottom of hero background
                    if (selectedTab == "MY_PROFILE") {
                        Surface(
                            modifier = Modifier
                                .size(90.dp)
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp),
                            shape = CircleShape,
                            color = Color.White,
                            shadowElevation = 4.dp
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
                                        Icons.Default.AccountCircle,
                                        contentDescription = null,
                                        tint = BluePrimary,
                                        modifier = Modifier.fillMaxSize().padding(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Tab Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    when (selectedTab) {
                        "DASHBOARD" -> DashboardTabContent()
                        "MANAGE_STUDENTS" -> ManageStudentsTabContent()
                        "MY_PROFILE" -> AdminProfileTabContent(
                            centerName = centerName,
                            centerLocation = centerLocation,
                            centerDescription = centerDescription,
                            achievements = achievements,
                            selectedImageUri = selectedImageUri,
                            operatingHours = operatingHours,
                            onUpdateCenter = { name, loc -> centerName = name; centerLocation = loc },
                            onUpdateDescription = { centerDescription = it },
                            onUpdateAchievements = { achievements = it },
                            onUpdateImage = { selectedImageUri = it },
                            onUpdateHours = { day, hours -> 
                                operatingHours = operatingHours.toMutableMap().apply { put(day, hours) }
                            }
                        )
                    }
                }
                
                // Pushes footer to the bottom of the screen if content is short
                Spacer(modifier = Modifier.weight(1f))
                
                // Admin Footer
                AdminFooter(
                    onTabSelect = { 
                        selectedTab = when(it) {
                            "MANAGE_STUDENTS" -> "MANAGE_STUDENTS"
                            "DASHBOARD" -> "DASHBOARD"
                            "MY_PROFILE" -> "MY_PROFILE"
                            else -> "DASHBOARD"
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DashboardTabContent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DashboardSummaryCard(
            modifier = Modifier.weight(1f),
            titleLines = listOf("Total", "Students", "Enrolled"),
            onClick = {}
        )
        DashboardSummaryCard(
            modifier = Modifier.weight(1f),
            titleLines = listOf("Pending", "Center", "Applications"),
            onClick = {}
        )
    }
}

@Composable
fun DashboardSummaryCard(modifier: Modifier = Modifier, titleLines: List<String>, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight()
                    .background(BluePrimary)
            )
            
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            titleLines.forEach { line ->
                                Text(
                                    text = line,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2D3748),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                        
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(0xFFEBF8FF), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = BluePrimary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(2.5.dp))
                            .background(BluePrimary)
                    )
                }
            }
        }
    }
}

@Composable
fun ManageStudentsTabContent() {
    Spacer(modifier = Modifier.height(16.dp))

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ManageStatCard(modifier = Modifier.weight(1f), label = "TOTAL STUDENTS", value = "0", subLabel = "All records")
            ManageStatCard(modifier = Modifier.weight(1f), label = "ENROLLED", value = "0", subLabel = "Active enrollment")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ManageStatCard(modifier = Modifier.weight(1f), label = "SCHEDULED", value = "0", subLabel = "Upcoming sessions")
            ManageStatCard(modifier = Modifier.weight(1f), label = "PENDING PAYMENT", value = "0", subLabel = "Awaiting confirmation")
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    ModernCalendar()

    Spacer(modifier = Modifier.height(12.dp))

    Card(
        modifier = Modifier.fillMaxWidth().height(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Select a day", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, fontSize = 15.sp)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(36.dp))
            Text("Click a day to see scheduled students", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(top = 6.dp))
            Spacer(modifier = Modifier.weight(1f))
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text("Students", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
    Spacer(modifier = Modifier.height(10.dp))
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), 
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("All", color = BluePrimary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Text("Enrolled", color = Color.Gray, fontSize = 11.sp)
                Text("Scheduled", color = Color.Gray, fontSize = 11.sp)
                Text("Pending Approval", color = Color.Gray, fontSize = 11.sp)
                Text("Cancelled", color = Color.Gray, fontSize = 11.sp)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Icon(Icons.Default.SearchOff, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(36.dp))
            Text("No students found.", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ModernCalendar() {
    var calendar by remember { mutableStateOf(Calendar.getInstance()) }
    var selectedDay by remember { mutableStateOf<Int?>(calendar.get(Calendar.DAY_OF_MONTH)) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { calendar = (calendar.clone() as Calendar).apply { add(Calendar.MONTH, -1) } },
                    modifier = Modifier.size(32.dp).border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = null, modifier = Modifier.size(16.dp))
                }
                
                val monthYear = remember(calendar) {
                    val months = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
                    "${months[calendar.get(Calendar.MONTH)]} ${calendar.get(Calendar.YEAR)}"
                }
                Text(monthYear, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1E293B))

                IconButton(
                    onClick = { calendar = (calendar.clone() as Calendar).apply { add(Calendar.MONTH, 1) } },
                    modifier = Modifier.size(32.dp).border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT").forEach {
                    Text(it, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val firstDayOfMonth = (calendar.clone() as Calendar).apply { set(Calendar.DAY_OF_MONTH, 1) }.get(Calendar.DAY_OF_WEEK) - 1
            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val today = Calendar.getInstance()
            val isCurrentMonth = today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
            val todayDay = today.get(Calendar.DAY_OF_MONTH)

            repeat(6) { rowIndex ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    repeat(7) { colIndex ->
                        val dayNumber = rowIndex * 7 + colIndex - firstDayOfMonth + 1
                        if (dayNumber in 1..daysInMonth) {
                            val isSelected = selectedDay == dayNumber
                            val isToday = isCurrentMonth && dayNumber == todayDay
                            
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(if (isSelected || isToday) CircleShape else RoundedCornerShape(8.dp))
                                    .background(
                                        when {
                                            isToday -> BluePrimary
                                            isSelected -> BluePrimary.copy(alpha = 0.1f)
                                            else -> Color(0xFFEFF6FF)
                                        }
                                    )
                                    .clickable { selectedDay = dayNumber },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayNumber.toString(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isToday) Color.White else Color(0xFF2563EB)
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.size(36.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LegendCircleItem(Color(0xFFEFF6FF), "Open")
                LegendCircleItem(BluePrimary, "Today")
                LegendCircleItem(Color.White, "Closed", hasBorder = true)
            }
        }
    }
}

@Composable
fun LegendCircleItem(color: Color, text: String, hasBorder: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(color).let { if(hasBorder) it.border(1.dp, Color(0xFFE2E8F0), CircleShape) else it })
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 10.sp, color = Color(0xFF64748B))
    }
}

@Composable
fun ManageStatCard(modifier: Modifier = Modifier, label: String, value: String, subLabel: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = label, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = BluePrimary, modifier = Modifier.padding(vertical = 1.dp))
            Text(text = subLabel, fontSize = 8.sp, color = Color.Gray)
        }
    }
}

@Composable
fun AdminProfileTabContent(
    centerName: String,
    centerLocation: String,
    centerDescription: String,
    achievements: List<String>,
    selectedImageUri: Uri?,
    operatingHours: Map<String, String>,
    onUpdateCenter: (String, String) -> Unit,
    onUpdateDescription: (String) -> Unit,
    onUpdateAchievements: (List<String>) -> Unit,
    onUpdateImage: (Uri) -> Unit,
    onUpdateHours: (String, String) -> Unit
) {
    var isEditingMain by remember { mutableStateOf(false) }
    var isEditingAbout by remember { mutableStateOf(false) }
    var isEditingAchievements by remember { mutableStateOf(false) }
    var isEditingSchedule by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { onUpdateImage(it) }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                if (isEditingMain) {
                    var nameInput by remember { mutableStateOf(centerName) }
                    var locInput by remember { mutableStateOf(centerLocation) }
                    
                    Text("Center Name", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    OutlinedTextField(
                        value = nameInput, 
                        onValueChange = { nameInput = it }, 
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 12.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                    )
                    
                    Text("Location", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    OutlinedTextField(
                        value = locInput, 
                        onValueChange = { locInput = it }, 
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
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { onUpdateCenter(nameInput, locInput); isEditingMain = false }, 
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                        ) { Text("Save", fontWeight = FontWeight.Bold) }
                        
                        Button(
                            onClick = { isEditingMain = false }, 
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                        ) { Text("Cancel", fontWeight = FontWeight.Bold) }
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = centerName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = buildAnnotatedString {
                                    append("Admin | review")
                                    withStyle(style = SpanStyle(color = MnlBlue, fontWeight = FontWeight.Bold)) { append(".mnl") }
                                    append(" partner | $centerLocation")
                                },
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                        Surface(
                            onClick = { isEditingMain = true },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, BluePrimary),
                            color = Color.White
                        ) {
                            Text("Edit Profile", fontSize = 11.sp, color = BluePrimary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = BluePrimary,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("About", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Surface(
                    onClick = { isEditingAbout = !isEditingAbout },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color.White),
                    color = Color.Transparent
                ) {
                    Text(if (isEditingAbout) "Done" else "Edit", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (isEditingAbout) {
                TextField(
                    value = centerDescription,
                    onValueChange = { onUpdateDescription(it) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
                )
            } else {
                Text(centerDescription, color = Color.White, fontSize = 13.sp)
            }
        }
    }

    Spacer(modifier = Modifier.height(14.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Achievements", color = BluePrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Surface(
                    onClick = { isEditingAchievements = !isEditingAchievements },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, BluePrimary),
                    color = Color.Transparent
                ) {
                    Text(if (isEditingAchievements) "Done" else "Edit", color = BluePrimary, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (isEditingAchievements) {
                var newAchievement by remember { mutableStateOf("") }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(value = newAchievement, onValueChange = { newAchievement = it }, modifier = Modifier.weight(1f), placeholder = { Text("Add achievement") })
                    IconButton(onClick = { if (newAchievement.isNotBlank()) { onUpdateAchievements(achievements + newAchievement); newAchievement = "" } }) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = BluePrimary)
                    }
                }
                achievements.forEachIndexed { index, ach ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(ach, modifier = Modifier.weight(1f), fontSize = 12.sp)
                        IconButton(onClick = { onUpdateAchievements(achievements.toMutableList().apply { removeAt(index) }) }) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            } else {
                achievements.forEach { ach ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color(0xFFFFB400), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(ach, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = BluePrimary)
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text("Operating Schedule", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
    Text("Days and hours the review center is open", color = Color.Gray, fontSize = 10.sp)
    
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
            ScheduleDayPillSmall(day, operatingHours[day] != "Closed")
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Opening Hours", fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.weight(1f))
                Surface(
                    onClick = { isEditingSchedule = !isEditingSchedule },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, BluePrimary),
                    color = Color.White
                ) {
                    Text(if (isEditingSchedule) "Done" else "Edit Schedule", color = BluePrimary, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            operatingHours.forEach { (day, hours) ->
                Row(modifier = Modifier.padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = day, modifier = Modifier.weight(1f), fontSize = 12.sp)
                    if (isEditingSchedule) {
                        Row(modifier = Modifier.weight(2f), verticalAlignment = Alignment.CenterVertically) {
                            var hourPart by remember { mutableStateOf(if(hours == "Closed") "8:00 AM" else hours.split(" - ")[0]) }
                            var endPart by remember { mutableStateOf(if(hours == "Closed") "6:00 PM" else hours.split(" - ").getOrElse(1){"6:00 PM"}) }
                            var isClosed by remember { mutableStateOf(hours == "Closed") }
                            
                            Checkbox(checked = isClosed, onCheckedChange = { 
                                isClosed = it
                                onUpdateHours(day, if(it) "Closed" else "$hourPart - $endPart")
                            })
                            Text("Closed", fontSize = 10.sp)
                            
                            if(!isClosed) {
                                OutlinedTextField(
                                    value = hourPart, 
                                    onValueChange = { hourPart = it; onUpdateHours(day, "$it - $endPart") },
                                    modifier = Modifier.width(75.dp).height(45.dp),
                                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 10.sp),
                                    singleLine = true
                                )
                                Text("-", modifier = Modifier.padding(horizontal = 4.dp))
                                OutlinedTextField(
                                    value = endPart, 
                                    onValueChange = { endPart = it; onUpdateHours(day, "$hourPart - $it") },
                                    modifier = Modifier.width(75.dp).height(45.dp),
                                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 10.sp),
                                    singleLine = true
                                )
                            }
                        }
                    } else {
                        Text(text = hours, color = if(hours == "Closed") Color.LightGray else Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleDayPillSmall(day: String, isActive: Boolean) {
    Surface(
        color = if (isActive) Color(0xFFEBF8FF) else Color.Transparent,
        shape = RoundedCornerShape(20.dp),
        border = if (!isActive) BorderStroke(1.dp, Color.LightGray) else null
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(if (isActive) BluePrimary else Color.LightGray))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = day, fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal, color = if (isActive) BluePrimary else Color.Gray, fontSize = 11.sp)
        }
    }
}
