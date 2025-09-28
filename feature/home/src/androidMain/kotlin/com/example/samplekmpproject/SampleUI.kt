package com.example.samplekmpproject

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// ----- UI State -----

data class DebtorDetailUiState(
    // Profile
    val name: String = "-",
    val userId: String = "-",
    val isPaidOff: Boolean = false,

    // Discount section
    val discountVisible: Boolean = true,

    // VA section
    val vaVisible: Boolean = true,

    // Loan info
    val dpdValue: String = "-",
    val outstandingAmount: String = "-",

    // Last payment
    val lastPayment: String = "-",
    val lastPaymentDate: String = "-",
    val lastPaymentMethod: String = "-",

    // User info (details)
    val fullName: String = "-",
    val gender: String = "-",
    val dob: String = "-",
    val religion: String = "-",
    val maritalStatus: String = "-",
    val phoneNumber: String = "-",
    val employerName: String = "-",
    val activationDate: String = "-",
)

// ----- Public entry -----

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DebtorDetailScreen(
    state: DebtorDetailUiState = DebtorDetailUiState(),
    onMakeReport: () -> Unit = {},
    onViewDiscount: () -> Unit = {},
    onViewVaNumber: () -> Unit = {},
    onCopy: (label: String, value: String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier,
) {
    val tabs = listOf(
        "Address", "Contact", "Visit History", "Payment History"
    )
    val pagerState = rememberPagerState { tabs.size }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Debtor Detail") },
                actions = {
                    IconButton(onClick = { /* settings action if needed */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onMakeReport,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) { Text("Make report") }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Profile Section
            item {
                SectionCard {
                    ProfileSection(
                        name = state.name,
                        userId = state.userId,
                        isPaidOff = state.isPaidOff
                    )
                }
            }

            // Discount Section
            if (state.discountVisible) {
                item { Spacer(Modifier.height(8.dp)) }
                item { Divider(thickness = 8.dp, color = lineSeparator()) }
                item {
                    SectionCard {
                        HeaderWithAction(
                            title = "Discount Program",
                            actionText = "View",
                            onAction = onViewDiscount
                        )
                        Spacer(Modifier.height(8.dp))
                        DashedDivider()
                    }
                }
            }

            // VA Section
            if (state.vaVisible) {
                item {
                    SectionCard {
                        HeaderWithAction(
                            title = "Virtual Account Number",
                            actionText = "View",
                            onAction = onViewVaNumber
                        )
                    }
                }
            }

            item { Divider(thickness = 8.dp, color = lineSeparator()) }

            // Loan info Section
            item {
                SectionCard(padding = 16.dp) {
                    Text(
                        text = "Loan Info",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    KeyValueRow(
                        label = "DPD",
                        value = state.dpdValue,
                        valueColor = MaterialTheme.colorScheme.primary,
                        topPadding = 12.dp
                    )
                    Spacer(Modifier.height(12.dp))
                    DashedDivider()
                    KeyValueRow(
                        label = "Outstanding Total",
                        value = state.outstandingAmount,
                        valueColor = MaterialTheme.colorScheme.primary,
                        topPadding = 12.dp
                    )
                    Spacer(Modifier.height(12.dp))
                    DashedDivider()
                }
            }

            item { Divider(thickness = 8.dp, color = lineSeparator()) }

            // Last Payment Section
            item {
                SectionCard(padding = 16.dp) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Last Payment",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand"
                        )
                    }

                    KeyValueRow(
                        label = "Last Payment",
                        value = state.lastPayment,
                        valueColor = MaterialTheme.colorScheme.primary,
                        topPadding = 12.dp
                    )
                    Spacer(Modifier.height(12.dp))
                    DashedDivider()

                    KeyValueRow(
                        label = "Payment Date",
                        value = state.lastPaymentDate,
                        valueColor = MaterialTheme.colorScheme.primary,
                        topPadding = 12.dp
                    )
                    Spacer(Modifier.height(12.dp))
                    DashedDivider()

                    KeyValueRow(
                        label = "Payment Method",
                        value = state.lastPaymentMethod,
                        valueColor = MaterialTheme.colorScheme.primary,
                        topPadding = 12.dp,
                        bottomPadding = 0.dp
                    )
                }
            }

            item { Divider(thickness = 8.dp, color = lineSeparator()) }

            // Personal Info (expandable)
            item {
                var expanded by remember { mutableStateOf(true) }
                SectionCard(padding = 16.dp) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "User Info",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = if (expanded) "Collapse" else "Expand",
                                modifier = Modifier
                            )
                        }
                    }

                    if (expanded) {
                        Column(Modifier.fillMaxWidth()) {
                            // Fullname (copyable)
                            CopyableRow(
                                label = "Full name",
                                value = state.fullName,
                                onCopy = { onCopy("Full name", state.fullName) }
                            )

                            KeyValueRow(label = "Gender", value = state.gender)
                            KeyValueRow(label = "Date of birth", value = state.dob)
                            KeyValueRow(label = "Religion", value = state.religion)
                            KeyValueRow(label = "Marital status", value = state.maritalStatus)

                            // Phone (copyable)
                            CopyableRow(
                                label = "Phone number",
                                value = state.phoneNumber,
                                onCopy = { onCopy("Phone", state.phoneNumber) }
                            )

                            KeyValueRow(label = "Employer name", value = state.employerName)
                            KeyValueRow(label = "Activation date", value = state.activationDate, bottomPadding = 0.dp)
                        }
                    }
                }
            }

            item { Divider(thickness = 8.dp, color = lineSeparator()) }

            // Tabs + Pager
            item {
                DebtorTabs(tabs = tabs, pagerState = pagerState)
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 12.dp,
                    flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                ) { page ->
                    // Placeholder konten tiap tab — ganti sesuaikan kebutuhan
                    SectionCard(padding = 16.dp) {
                        Text(
                            text = when (page) {
                                0 -> "Address content"
                                1 -> "Contact content"
                                2 -> "Visit history content"
                                else -> "Payment history content"
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(80.dp)) } // memberi ruang di atas bottom bar
        }
    }
}

// ----- Sub-composables -----

@Composable
private fun ProfileSection(
    name: String,
    userId: String,
    isPaidOff: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar placeholder (120dp)
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(color = Color(0xFFEFEFEF), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("IMG", color = Color.DarkGray)
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 2, overflow = TextOverflow.Ellipsis
            )
            Text(
                text = userId,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6E6E6E)
            )
            if (isPaidOff) {
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(
                            color = Color(0xFFE6F7ED),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "PAID OFF",
                        color = Color(0xFF219653),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderWithAction(
    title: String,
    actionText: String,
    onAction: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6E6E6E),
            modifier = Modifier.weight(1f)
        )
        TextButton(onClick = onAction) {
            Text(text = actionText, color = Color(0xFF219653), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun KeyValueRow(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
    topPadding: Dp = 8.dp,
    bottomPadding: Dp = 8.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding, bottom = bottomPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color(0xFF6E6E6E),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = valueColor,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
private fun CopyableRow(
    label: String,
    value: String,
    onCopy: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color(0xFF6E6E6E),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(onClick = onCopy) {
//                Icon(Icons.Default.ContentCopy, contentDescription = "Copy $label")
            }
        }
    }
}

@Composable
private fun SectionCard(
    modifier: Modifier = Modifier,
    padding: Dp = 12.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 1.dp,
        shadowElevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(padding), content = content)
    }
}

@Composable
private fun lineSeparator(): Color = Color(0xFFEDEDED)

@Composable
private fun DashedDivider(
    color: Color = lineSeparator(),
    thickness: Dp = 2.dp,
    dashWidth: Dp = 6.dp,
    gapWidth: Dp = 6.dp
) {
    val thicknessPx = with(LocalDensity.current) { thickness.toPx() }
    val dashPx = with(LocalDensity.current) { dashWidth.toPx() }
    val gapPx = with(LocalDensity.current) { gapWidth.toPx() }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        var startX = 0f
        while (startX < size.width) {
            val endX = (startX + dashPx).coerceAtMost(size.width)
            drawLine(
                color = color,
                start = Offset(startX, size.height / 2f),
                end = Offset(endX, size.height / 2f),
                strokeWidth = thicknessPx,
                cap = StrokeCap.Round
            )
            startX += (dashPx + gapPx)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DebtorTabs(
    tabs: List<String>,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                text = { Text(title) }
            )
        }
    }
}