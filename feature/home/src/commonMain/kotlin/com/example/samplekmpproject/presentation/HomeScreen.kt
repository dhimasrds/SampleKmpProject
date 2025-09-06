package com.example.samplekmpproject.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.example.samplekmpproject.HomeScreenView


data class HomeScreen(val username: String) : Screen {
    @Composable
    override fun Content() {
        HomeScreenView(username) // expect function
    }
}

