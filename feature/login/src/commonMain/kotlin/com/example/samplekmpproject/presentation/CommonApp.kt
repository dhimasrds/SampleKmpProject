package com.example.samplekmpproject.presentation

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

/**
 * CommonApp()
 * - Titik masuk UI lintas platform (Android/iOS/Desktop).
 * - Di-call dari entry point tiap platform (Activity Android, UIViewController iOS, Window Desktop).
 * - Menyediakan tema dan state bersama.
 */
class CommonApp(): Screen {
    @Composable
    override fun Content() {
        // Adding a wrapping MaterialTheme for consistent styling
        MaterialTheme {
            // Gunakan rememberSaveable agar nilai bertahan saat rotasi Android / recreation.
            var count by rememberSaveable { mutableStateOf(0) }

            // Column layout sederhana untuk center content.
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Heading sederhana
                Text("Hello KMP 👋", style = MaterialTheme.typography.headlineMedium)

                Spacer(Modifier.height(12.dp))

                // Banner untuk menampilkan platform aktif (Android/iOS/Desktop).
                PlatformBanner()

                Spacer(Modifier.height(12.dp))

                // Tampilkan nilai counter
                Text("Clicks: $count", style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(16.dp))

                // Tombol yang menaikkan counter ketika ditekan.
                Button(onClick = { count++ }) {
                    Text("Tap me")
                }
            }
        }
    }
}

@Composable
fun PlatformBanner() {
    Text(
        "Running on: Android", 
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyLarge
    )
}

/**
 * Convenience function to create a CommonApp wrapped in MaterialTheme
 * and Navigator for use in previews and app entry points
 */
@Composable
fun commonApp() {
    MaterialTheme {
        Navigator(screen = CommonApp())
    }
}



/**
 * PlatformBanner()
 * - Menampilkan teks "Running on: <platform>" memakai expect/actual platformName().
 * - Contoh pemakaian shared UI + API spesifik via expect/actual.
 */
//@Composable
//fun PlatformBanner() {
//    Text("Running on: ${platformName()}")
//}
