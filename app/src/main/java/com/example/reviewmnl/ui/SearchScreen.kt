package com.example.reviewmnl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.R
import com.example.reviewmnl.data.api.RetrofitClient
import com.example.reviewmnl.data.model.ReviewCenterDto
import com.example.reviewmnl.data.model.awaitResult
import com.example.reviewmnl.data.model.toStringList
import com.example.reviewmnl.ui.theme.BluePrimary
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToContact: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedRating by remember { mutableStateOf<Int?>(null) }
    var showFilters by remember { mutableStateOf(false) }
    var allCenters by remember { mutableStateOf(reviewCenters) }
    var isSearchLoading by remember { mutableStateOf(false) }

    // Load all centers from API on first composition
    LaunchedEffect(Unit) {
        try {
            val apiList = RetrofitClient.apiService.getCenters().awaitResult()
            if (apiList.isNotEmpty()) {
                allCenters = apiList.map { it.toReviewCenter() }
            }
        } catch (_: Exception) { /* keep local fallback */ }
    }

    // When the search query changes, debounce then call the API search endpoint.
    // LaunchedEffect cancels the previous coroutine (and its in-flight HTTP request)
    // when the key (searchQuery) changes, providing automatic debouncing cancellation.
    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 2) {
            kotlinx.coroutines.delay(300) // debounce: wait 300 ms before firing
            isSearchLoading = true
            try {
                val apiList = RetrofitClient.apiService.searchCenters(searchQuery).awaitResult()
                allCenters = apiList.map { it.toReviewCenter() }
            } catch (_: Exception) {
                // keep current allCenters on network error
            } finally {
                isSearchLoading = false
            }
        } else if (searchQuery.isEmpty()) {
            // When the query is cleared, reload the full list
            try {
                val apiList = RetrofitClient.apiService.getCenters().awaitResult()
                if (apiList.isNotEmpty()) allCenters = apiList.map { it.toReviewCenter() }
            } catch (_: Exception) { /* keep current */ }
        }
    }

    // Apply UI-local filters (category & rating) on top of the current allCenters list.
    // Name/text filtering is handled server-side when searchQuery.length >= 2; for shorter
    // queries we also filter locally so the list is never stale.
    val filteredCenters = allCenters.filter { center ->
        val matchesSearch = searchQuery.length >= 2 || center.name.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == null || center.category == selectedCategory
        val matchesRating = selectedRating == null || center.rating.roundToInt() == selectedRating
        matchesSearch && matchesCategory && matchesRating
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(BluePrimary)
    ) {
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .heightIn(min = screenHeight)
                    .statusBarsPadding()
            ) {
                // Search Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bgsearch),
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
                                    startY = 200f
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                        
                        Text(
                            text = "Search",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Text(
                            text = "And Filter",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
                        )

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            shadowElevation = 2.dp
                        ) {
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.fillMaxSize(),
                                singleLine = true,
                                textStyle = TextStyle(
                                    color = Color.Black,
                                    fontSize = 15.sp
                                ),
                                cursorBrush = SolidColor(BluePrimary),
                                decorationBox = { innerTextField ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 20.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(modifier = Modifier.weight(1f)) {
                                            if (searchQuery.isEmpty()) {
                                                Text(
                                                    text = "Search centers...",
                                                    color = Color.Gray,
                                                    fontSize = 15.sp
                                                )
                                            }
                                            innerTextField()
                                        }
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = null,
                                            tint = Color.Gray,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }

                // Filters Section
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.clickable { showFilters = !showFilters },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Filters", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Icon(imageVector = if (showFilters) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
                        
                        if (selectedCategory != null || selectedRating != null) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                modifier = Modifier.clickable { 
                                    selectedCategory = null
                                    selectedRating = null
                                },
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Clear all", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }
                    }

                    if (showFilters) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Category", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        FlowRow(
                            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            categories.forEach { category ->
                                FilterPill(
                                    label = category.name,
                                    isSelected = selectedCategory == category.name,
                                    onClick = { selectedCategory = if (selectedCategory == category.name) null else category.name }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Ratings", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Row(modifier = Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(5, 4, 3, 2, 1).forEach { stars ->
                                FilterPill(
                                    label = "$stars " + "★".repeat(stars),
                                    isSelected = selectedRating == stars,
                                    onClick = { selectedRating = if (selectedRating == stars) null else stars }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Results (${filteredCenters.size})", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f))
                        if (isSearchLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp).padding(end = 8.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        }
                        TextButton(onClick = onBack) {
                            Text("DONE", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Results List
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        filteredCenters.forEach { center ->
                            LargeReviewCenterCard(
                                center = center,
                                onClick = {
                                    val dest = if (center.id > 0) center.id.toString() else center.name
                                    onNavigateToDetail(dest)
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))

                // Footer Section
                SimpleFooter(
                    onNavigateToHome = onNavigateToHome,
                    onNavigateToContact = onNavigateToContact,
                    onNavigateToSearch = { /* Already here */ }
                )
            }
        }
    }
}
