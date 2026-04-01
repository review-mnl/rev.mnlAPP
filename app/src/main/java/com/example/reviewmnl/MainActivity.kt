package com.example.reviewmnl

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reviewmnl.ui.HomeScreen
import com.example.reviewmnl.ui.LoginScreen
import com.example.reviewmnl.ui.SearchScreen
import com.example.reviewmnl.ui.ReviewCenterDetailScreen
import com.example.reviewmnl.ui.MessagesScreen
import com.example.reviewmnl.ui.ChatDetailScreen
import com.example.reviewmnl.ui.ContactScreen
import com.example.reviewmnl.ui.ProfileScreen
import com.example.reviewmnl.ui.theme.ReviewmnlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReviewmnlTheme {
                ReviewMnlApp()
            }
        }
    }
}

@Composable
fun ReviewMnlApp() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }
    var currentUser by remember { mutableStateOf<com.example.reviewmnl.ui.User?>(null) }
    val context = LocalContext.current
    
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                isLoggedIn = isLoggedIn,
                currentUser = currentUser,
                onNavigateToLogin = { navController.navigate("login") },
                onLogout = { 
                    isLoggedIn = false
                    currentUser = null
                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                },
                onNavigateToSearch = {
                    navController.navigate("search")
                },
                onNavigateToDetail = { centerName ->
                    navController.navigate("detail/$centerName")
                },
                onNavigateToMessages = {
                    if (isLoggedIn) {
                        navController.navigate("messages")
                    } else {
                        Toast.makeText(context, "Please login to view messages", Toast.LENGTH_SHORT).show()
                    }
                },
                onNavigateToContact = {
                    navController.navigate("contact")
                },
                onNavigateToProfile = {
                    if (isLoggedIn) navController.navigate("profile") else navController.navigate("login")
                },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
        composable("login") {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onLoginSuccess = { email ->
                    // Set logged in state and a mock user object
                    isLoggedIn = true
                    currentUser = com.example.reviewmnl.ui.User(name = "Zaki The Creator", email = email)
                    Toast.makeText(context, "Welcome, $email!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack("home", inclusive = false)
                }
            )
        }
        composable("search") {
            SearchScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onNavigateToContact = {
                    navController.navigate("contact")
                },
                onNavigateToDetail = { centerName ->
                    navController.navigate("detail/$centerName")
                }
            )
        }
        composable("detail/{centerName}") { backStackEntry ->
            val centerName = backStackEntry.arguments?.getString("centerName") ?: ""
            ReviewCenterDetailScreen(
                centerName = centerName,
                isLoggedIn = isLoggedIn,
                currentUser = currentUser,
                onBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onNavigateToContact = {
                    navController.navigate("contact")
                },
                onNavigateToSearch = {
                    navController.navigate("search")
                },
                onNavigateToProfile = {
                    if (isLoggedIn) navController.navigate("profile") else navController.navigate("login")
                },
                onLogout = {
                    isLoggedIn = false
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onNavigateToMessages = {
                    if (isLoggedIn) {
                        navController.navigate("messages")
                    } else {
                        Toast.makeText(context, "Please login to message", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                user = currentUser,
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToSearch = { navController.navigate("search") },
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToContact = { navController.navigate("contact") },
                isLoggedIn = isLoggedIn,
                onLogout = {
                    isLoggedIn = false
                    currentUser = null
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                }
                ,
                onUpdateUser = { updated ->
                    currentUser = updated
                }
            )
        }
        composable("messages") {
            MessagesScreen(
                onBack = { navController.popBackStack() },
                onNavigateToChat = { chatName ->
                    navController.navigate("chatDetail/$chatName")
                }
            )
        }
        composable("chatDetail/{chatName}") { backStackEntry ->
            val chatName = backStackEntry.arguments?.getString("chatName") ?: ""
            ChatDetailScreen(
                chatName = chatName,
                onBack = { navController.popBackStack() }
            )
        }
        composable("contact") {
            ContactScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToSearch = { navController.navigate("search") },
                onNavigateToLogin = { navController.navigate("login") },
                isLoggedIn = isLoggedIn,
                onLogout = { 
                    isLoggedIn = false 
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                }
            )
        }
    }
}
