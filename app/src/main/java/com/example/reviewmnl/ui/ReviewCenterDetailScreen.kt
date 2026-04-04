package com.example.reviewmnl.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.reviewmnl.R
import com.example.reviewmnl.ui.theme.BluePrimary
import java.util.*

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
    val context = LocalContext.current
    var showBookingDialog by remember { mutableStateOf(false) }

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
                    LogoText(
                        fontSize = 18.sp,
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
                textAlign = TextAlign.Justify
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
                        onClick = { 
                            if (isLoggedIn) showBookingDialog = true 
                            else Toast.makeText(context, "Please login to book an appointment", Toast.LENGTH_SHORT).show()
                        },
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

        if (showBookingDialog) {
            BookingDialog(
                centerName = center.name,
                onDismiss = { showBookingDialog = false },
                onConfirm = { date, time, program, payment ->
                    showBookingDialog = false
                    Toast.makeText(context, "Appointment booked for $program on $date at $time. Paid via $payment.", Toast.LENGTH_LONG).show()
                    onNavigateToMessages()
                },
                programs = center.programs
            )
        }
    }
}

@Composable
fun BookingDialog(
    centerName: String,
    programs: List<String>,
    onDismiss: () -> Unit,
    onConfirm: (date: String, time: String, program: String, payment: String) -> Unit
) {
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var selectedProgram by remember { mutableStateOf(programs.firstOrNull() ?: "") }
    var selectedPayment by remember { mutableStateOf("GCash") }
    var expanded by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Book Appointment",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary
                )
                Text(
                    text = "Schedule your visit to $centerName",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                // Program Selection
                Text("Select Program", modifier = Modifier.fillMaxWidth(), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Surface(
                        modifier = Modifier.fillMaxWidth().clickable { expanded = true },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color.LightGray),
                        color = Color.White
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = selectedProgram, modifier = Modifier.weight(1f), fontSize = 14.sp)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        programs.forEach { program ->
                            DropdownMenuItem(
                                text = { Text(program) },
                                onClick = {
                                    selectedProgram = program
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Date Picker
                Button(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                selectedDate = "${month + 1}/$dayOfMonth/$year"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5), contentColor = Color.Black),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (selectedDate.isEmpty()) "Select Date" else selectedDate, fontSize = 14.sp)
                }

                // Time Picker
                Button(
                    onClick = {
                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                val ampm = if (hourOfDay < 12) "AM" else "PM"
                                val hour = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
                                selectedTime = String.format(Locale.US, "%02d:%02d %s", hour, minute, ampm)
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5), contentColor = Color.Black),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (selectedTime.isEmpty()) "Select Time" else selectedTime, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Method
                Text("Payment Method", modifier = Modifier.fillMaxWidth(), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BluePrimary)
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { selectedPayment = "GCash" }.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = selectedPayment == "GCash", onClick = { selectedPayment = "GCash" }, colors = RadioButtonDefaults.colors(selectedColor = BluePrimary))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("GCash", fontSize = 14.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Box(modifier = Modifier.size(24.dp).background(Color(0xFF007DFE), RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {
                            Text("G", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { selectedPayment = "Cash" }.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = selectedPayment == "Cash", onClick = { selectedPayment = "Cash" }, colors = RadioButtonDefaults.colors(selectedColor = BluePrimary))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cash on Site", fontSize = 14.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(Icons.Default.Payments, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                    }
                }

                if (selectedPayment == "GCash") {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F7FF)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Booking Fee: ₱50.00", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = BluePrimary)
                            Text("Pay to: 0912 345 6789 (Z.T.C.)", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = { onConfirm(selectedDate, selectedTime, selectedProgram, selectedPayment) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                        enabled = selectedDate.isNotEmpty() && selectedTime.isNotEmpty()
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}
