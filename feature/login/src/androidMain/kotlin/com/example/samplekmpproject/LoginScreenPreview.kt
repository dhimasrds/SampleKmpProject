package com.example.samplekmpproject

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.example.samplekmpproject.presentation.CommonApp
import com.example.samplekmpproject.presentation.commonApp

@Preview(
    showBackground = true,
    apiLevel = 35,
    device = Devices.PIXEL_4
)
@Composable
fun LoginScreenPreview() {
    // Add Surface wrapper to ensure content is visible in preview
    Surface {
        commonApp()
    }
}