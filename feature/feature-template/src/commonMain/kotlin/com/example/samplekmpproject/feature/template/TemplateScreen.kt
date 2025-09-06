package com.example.samplekmpproject.feature.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Minimal composable to serve as a starting point for a new feature.
 */
@Composable
fun TemplateScreen(onAction: (() -> Unit)? = null) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Feature Template", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = { onAction?.invoke() }) {
            Text("Do something")
        }
    }
}
