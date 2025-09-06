package com.example.samplekmpproject.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object ScreenA : Screen {
    @Composable
    override fun Content() {
        Text("This is Screen A")
    }
}

object ScreenB : Screen {
    @Composable
    override fun Content() {
        Text("This is Screen B")
    }
}

object ScreenC : Screen {
    @Composable
    override fun Content() {
        Text("This is Screen C")
    }
}