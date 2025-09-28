package com.example.samplekmpproject

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
actual fun CarouselSection() {
    Column {
        Text(
            text = "Highlights",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        // Make the carousel responsive by adapting item width to the available space.
        // We use BoxWithConstraints to read the maxWidth and compute how many items
        // should be visible per "page" on desktop. Tweak the breakpoints as needed.
        androidx.compose.foundation.layout.BoxWithConstraints {
            val contentPadding = 16.dp
            val spacing = 12.dp
            val availableWidth = maxWidth - contentPadding * 2

            val itemsPerPage = when {
                maxWidth < 600.dp -> 1
                maxWidth < 900.dp -> 2
                maxWidth < 1400.dp -> 3
                else -> 4
            }
            val cardWidth = (availableWidth - spacing * (itemsPerPage - 1)) / itemsPerPage
            val cardHeight = cardWidth * 0.6f // keep ~16:9-ish aspect ratio

            // Define items outside LazyRow so gestures can reference the size
            val carouselItems = listOf("Banner 1", "Banner 2", "Banner 4", "Banner 5", "Banner 6", "Banner 7")

            Box {
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                scope.launch {
                                    listState.scrollBy(-dragAmount.x)
                                }
                            }
                        },
                    contentPadding = PaddingValues(horizontal = contentPadding),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    items(carouselItems) { banner ->
                        Card(
                            modifier = Modifier
                                .width(cardWidth)
                                .height(cardHeight)
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

                HorizontalScrollbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = contentPadding),
                    adapter = rememberScrollbarAdapter(listState)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun HomeScreenView(username: String) {
    // Use the shared HomeScreenContent which includes navigation functionality
    HomeScreenContent(username)
}
