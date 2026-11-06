package com.example.panitiahub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panitiahub.data.LogisticsItem
import com.example.panitiahub.data.SampleData
import com.example.panitiahub.ui.components.AppBottomNavigationBar
import com.example.panitiahub.ui.components.LogisticsItemCard
import com.example.panitiahub.ui.theme.AppBackground
import com.example.panitiahub.ui.theme.PanitiaHubTheme
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary
import com.example.panitiahub.ui.theme.UnselectedTabBg
import com.example.panitiahub.ui.theme.UnselectedTabText

@Composable
fun LogisticsPagerScreen(
    items: List<LogisticsItem>,
    onItemClick: (LogisticsItem) -> Unit,
    onReturnClick: (LogisticsItem) -> Unit,
    onAddClick: () -> Unit,
    onNavigateRoute: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTabIsReturned by remember { mutableStateOf(false) }

    val borrowedCount = items.count { !it.isReturned }
    val returnedCount = items.count { it.isReturned }

    val filteredItems = items.filter { it.isReturned == selectedTabIsReturned }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppBackground,
        bottomBar = {
            AppBottomNavigationBar(
                selectedRoute = "logistics",
                onRouteSelected = onNavigateRoute
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape,
                containerColor = PrimaryBlue,
                contentColor = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Peminjaman",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Screen Header Title
            Text(
                text = "Peminjaman Logistik & HT",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle
            Text(
                text = "Pantau alat yang dipakai di lapangan.",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Segmented Filter Tabs
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tab 1: Sedang Dipinjam
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(if (!selectedTabIsReturned) PrimaryBlue else UnselectedTabBg)
                        .clickable { selectedTabIsReturned = false }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Sedang Dipinjam ($borrowedCount)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (!selectedTabIsReturned) Color.White else UnselectedTabText
                    )
                }

                // Tab 2: Sudah Kembali
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(if (selectedTabIsReturned) PrimaryBlue else UnselectedTabBg)
                        .clickable { selectedTabIsReturned = true }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Sudah Kembali ($returnedCount)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selectedTabIsReturned) Color.White else UnselectedTabText
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logistics Cards List
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(
                    items = filteredItems,
                    key = { it.id }
                ) { item ->
                    LogisticsItemCard(
                        item = item,
                        onCardClick = { onItemClick(item) },
                        onReturnClick = { onReturnClick(item) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun LogisticsPagerScreenPreview() {
    PanitiaHubTheme {
        LogisticsPagerScreen(
            items = SampleData.initialItems,
            onItemClick = {},
            onReturnClick = {},
            onAddClick = {}
        )
    }
}
