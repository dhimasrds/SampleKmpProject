package com.example.samplekmpproject

// androidMain/kotlin/Preview.kt
// (Opsional tapi direkomendasikan untuk iterasi cepat)

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import com.example.samplekmpproject.presentation.commonApp

/**
 * Preview Android (Light)
 * - Mempercepat iterasi UI tanpa run ke emulator.
 */
@Preview(
    name = "CommonApp Light",
    showBackground = true
)
@Composable
fun PreviewCommonAppLight() {
    commonApp()
}

/**
 * Preview Android (Dark + simulasi night mode)
 */
@Preview(
    name = "CommonApp Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewCommonAppDark() { commonApp() }
