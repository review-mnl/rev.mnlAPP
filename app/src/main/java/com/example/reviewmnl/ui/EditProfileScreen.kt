package com.example.reviewmnl.ui

import androidx.compose.runtime.Composable

@Deprecated("Inline editing is now used in ProfileScreen.kt", level = DeprecationLevel.WARNING)
@Composable
fun EditProfileScreen(
    user: User?,
    onSave: (User) -> Unit,
    onCancel: () -> Unit
) {
    // This component is no longer used and can be removed
}
