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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.samplekmpproject.HomeScreenView
import com.example.account.AccountScreen


data class HomeScreen(val username: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Navigation bar with Account button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Welcome, $username",
                    style = MaterialTheme.typography.headlineSmall
                )

                Button(
                    onClick = {
                        navigator.push(AccountScreen(username))
                    }
                ) {
                    Text("Account")
                }
            }

            Divider()

            // Original home content
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreenView(username) // expect function
            }
        }
    }
}
