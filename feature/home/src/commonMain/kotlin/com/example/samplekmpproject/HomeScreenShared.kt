package com.example.samplekmpproject

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.samplekmpproject.presentation.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Preview
@Composable
private fun CarouselSectionCommon() {
    Column {
        Text(
            text = "Highlights",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val carouselItems = listOf("Banner 1", "Banner 2", "Banner 3")
            items(carouselItems) { banner ->
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(120.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(banner, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
@Preview
@Composable
private fun ListSectionCommon(items: List<String>) {
    Column {
        items.forEach { item ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
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
                item { CarouselSectionCommon() }
                item { Spacer(Modifier.height(24.dp)) }
                item { ListSectionCommon(listOf("Profile", "Settings", "Logout", "Account")) }
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
