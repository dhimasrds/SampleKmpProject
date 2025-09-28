package com.example.samplekmpproject

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import com.example.samplekmpproject.presentation.HomeScreen
import com.example.samplekmpproject.presentation.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        // 🚀 Navigation Flow: LoginScreen > HomeScreen > AccountScreen
        Navigator(
            screen = LoginScreen { navigator, username ->
                // ✅ Navigation dari LoginScreen ke HomeScreen
                navigator.push(HomeScreen(username))
            }
        )
    }
}
