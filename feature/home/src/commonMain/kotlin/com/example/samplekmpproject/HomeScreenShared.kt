package com.example.samplekmpproject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.account.AccountScreen
import com.example.samplekmpproject.presentation.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

// Create an expect function for platform-specific carousel
@Composable
expect fun CarouselSection()

@Preview
@Composable
private fun ListSectionCommon(items: List<String>, onItemClick: (String) -> Unit = {}) {
    Column {
        items.forEach { item ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(username: String) {
    val navigator = LocalNavigator.currentOrThrow
    val viewModelOrNull = remember {
        runCatching { object : KoinComponent {}.get<HomeViewModel>() }.getOrNull()
    }
    if (viewModelOrNull == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Dependency injection not initialized. Please check Koin startup.")
        }
        return
    }
    val viewModel = viewModelOrNull
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.todos, state.errorMessage) {
        if (state.errorMessage == null && state.todos.isNotEmpty()) {
            println("[Home] Success: received ${state.todos.size} todos. First item: ${state.todos.firstOrNull()}")
        }
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.clear() }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Welcome, $username") }) }
        ) { padding ->
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize()
            ) {
                item { CarouselSection() } // Use platform-specific carousel
                item { Spacer(Modifier.height(24.dp)) }
                item {
                    ListSectionCommon(
                        items = listOf("Profile", "Settings", "Logout", "Account"),
                        onItemClick = { item ->
                            when (item) {
                                "Account" -> navigator.push(AccountScreen(username))
                                "Profile" -> { /* Handle profile navigation */ }
                                "Settings" -> { /* Handle settings navigation */ }
                                "Logout" -> { /* Handle logout */ }
                            }
                        }
                    )
                }
            }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.errorMessage?.let { msg ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $msg")
            }
        }

        androidx.compose.material3.FloatingActionButton(
            onClick = { /* TODO: action */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Text("+")
        }
    }
}
