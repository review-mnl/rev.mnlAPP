package com.example.reviewmnl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reviewmnl.R
import com.example.reviewmnl.ui.theme.BluePrimary
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedRating by remember { mutableStateOf<Int?>(null) }
    var showFilters by remember { mutableStateOf(false) }

    val filteredCenters = reviewCenters.filter { center ->
        val matchesSearch = center.name.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == null || center.category == selectedCategory
        val matchesRating = selectedRating == null || center.rating.roundToInt() == selectedRating
        matchesSearch && matchesCategory && matchesRating
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BluePrimary)
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
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Search...", color = Color.Gray) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                    }
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
                TextButton(onClick = onBack) {
                    Text("DONE", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredCenters) { center ->
                    LargeReviewCenterCard(
                        center = center,
                        onClick = { onNavigateToDetail(center.name) }
                    )
                }
            }
        }
    }
}
