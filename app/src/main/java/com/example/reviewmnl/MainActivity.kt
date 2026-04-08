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
import com.example.reviewmnl.data.local.TokenManager
import com.example.reviewmnl.ui.HomeScreen
import com.example.reviewmnl.ui.LoginScreen
import com.example.reviewmnl.ui.SearchScreen
import com.example.reviewmnl.ui.ReviewCenterDetailScreen
import com.example.reviewmnl.ui.MessagesScreen
import com.example.reviewmnl.ui.ChatDetailScreen
import com.example.reviewmnl.ui.ContactScreen
import com.example.reviewmnl.ui.ProfileScreen
import com.example.reviewmnl.ui.AdminDashboardScreen
import com.example.reviewmnl.ui.UserType
import com.example.reviewmnl.ui.theme.ReviewmnlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize TokenManager so it can persist the JWT token across sessions
        TokenManager.init(this)
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

    fun performLogout() {
        isLoggedIn = false
        currentUser = null
        TokenManager.clear()
    }
    
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                isLoggedIn = isLoggedIn,
                currentUser = currentUser,
                onNavigateToLogin = { navController.navigate("login") },
                onLogout = {
                    performLogout()
                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                },
                onNavigateToSearch = {
                    navController.navigate("search")
                },
                onNavigateToDetail = { centerIdentifier ->
                    navController.navigate("detail/$centerIdentifier")
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
                    if (isLoggedIn) {
                        if (currentUser?.userType == UserType.ADMIN) {
                            navController.navigate("admin_dashboard")
                        } else {
                            navController.navigate("profile")
                        }
                    } else {
                        navController.navigate("login")
                    }
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
                onLoginSuccess = { email, isStudent ->
                    isLoggedIn = true
                    val type = if (isStudent) UserType.STUDENT else UserType.ADMIN
                    currentUser = com.example.reviewmnl.ui.User(
                        name = email.substringBefore("@").replaceFirstChar { it.uppercase() },
                        email = email,
                        userType = type,
                        role = if (isStudent) "Student | review.mnl member" else "Admin | review.mnl partner"
                    )
                    
                    Toast.makeText(context, "Welcome, $email!", Toast.LENGTH_SHORT).show()
                    
                    if (type == UserType.ADMIN) {
                        navController.navigate("admin_dashboard") {
                            popUpTo("home") { inclusive = true }
                        }
                    } else {
                        navController.popBackStack("home", inclusive = false)
                    }
                }
            )
        }
        composable("admin_dashboard") {
            AdminDashboardScreen(
                user = currentUser,
                onLogout = {
                    performLogout()
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onNavigateToMessages = {
                    navController.navigate("messages")
                },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
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
                onNavigateToDetail = { centerIdentifier ->
                    navController.navigate("detail/$centerIdentifier")
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
                    if (isLoggedIn) {
                        if (currentUser?.userType == UserType.ADMIN) {
                            navController.navigate("admin_dashboard")
                        } else {
                            navController.navigate("profile")
                        }
                    } else {
                        navController.navigate("login")
                    }
                },
                onLogout = {
                    performLogout()
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
                    performLogout()
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                },
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
                    performLogout()
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                }
            )
        }
    }
}
