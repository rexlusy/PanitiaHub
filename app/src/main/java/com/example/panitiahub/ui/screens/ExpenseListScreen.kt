package com.example.panitiahub.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panitiahub.data.ExpenseItem
import com.example.panitiahub.data.SampleExpenseData
import com.example.panitiahub.ui.components.AppBottomNavigationBar
import com.example.panitiahub.ui.theme.AppBackground
import com.example.panitiahub.ui.theme.BorderGray
import com.example.panitiahub.ui.theme.CardBackground
import com.example.panitiahub.ui.theme.PanitiaHubTheme
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.TextDestructive
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary
import com.example.panitiahub.ui.theme.UnselectedTabBg
import com.example.panitiahub.ui.theme.UnselectedTabText
import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(amount: Long): String {
    val localeID = Locale("id", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    val result = format.format(amount)
    return result.replace(",00", "").replace("Rp", "Rp")
}

@Composable
fun ReceiptThumbnailCanvas(
    modifier: Modifier = Modifier,
    strokeColor: Color = Color(0xFF9CA3AF)
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.8.dp.toPx()
        val width = size.width
        val height = size.height

        // Outer paper rectangle
        drawRoundRect(
            color = strokeColor,
            topLeft = Offset(width * 0.1f, height * 0.1f),
            size = Size(width * 0.8f, height * 0.8f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = strokeWidth)
        )

        // Receipt content lines
        drawLine(
            color = strokeColor,
            start = Offset(width * 0.25f, height * 0.3f),
            end = Offset(width * 0.75f, height * 0.3f),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = strokeColor,
            start = Offset(width * 0.25f, height * 0.48f),
            end = Offset(width * 0.65f, height * 0.48f),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = strokeColor,
            start = Offset(width * 0.25f, height * 0.66f),
            end = Offset(width * 0.75f, height * 0.66f),
            strokeWidth = strokeWidth
        )
    }
}

@Composable
fun ExpenseListScreen(
    expenses: List<ExpenseItem>,
    onExpenseClick: (ExpenseItem) -> Unit,
    onAddExpenseClick: () -> Unit,
    onNavigateRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("Semua") }

    val categories = listOf("Semua", "Perlengkapan", "Konsumsi", "PDD", "Transportasi")

    val filteredExpenses = if (selectedCategory == "Semua") {
        expenses
    } else {
        expenses.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    val totalAmount = expenses.sumOf { it.amount }
    val formattedTotal = formatRupiah(if (totalAmount > 0) totalAmount else SampleExpenseData.totalExpense)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppBackground,
        bottomBar = {
            AppBottomNavigationBar(
                selectedRoute = "finance",
                onRouteSelected = onNavigateRoute
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpenseClick,
                shape = CircleShape,
                containerColor = PrimaryBlue,
                contentColor = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Catatan Kas",
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

            // Title Header
            Text(
                text = "Kas & Bukti Belanja",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle
            Text(
                text = "Semua transaksi tim, tercatat transparan.",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Main Blue Summary Card (Total Pengeluaran)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlue),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Total Pengeluaran",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = formattedTotal,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Receipt count row
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ReceiptLong,
                            contentDescription = "Nota",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${expenses.count { it.hasReceipt }} Nota Tercatat",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Progress Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.77f)
                                .height(6.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Sisa anggaran Rp1.150.000",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section Header
            Text(
                text = "Riwayat Transaksi",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Filter Chips Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) PrimaryBlue else UnselectedTabBg)
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) Color.White else UnselectedTabText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Transactions List
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = filteredExpenses,
                    key = { it.id }
                ) { item ->
                    ExpenseCardItem(
                        item = item,
                        onClick = { onExpenseClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpenseCardItem(
    item: ExpenseItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Receipt Image Thumbnail Box
            Box(
                modifier = Modifier
                    .size(width = 44.dp, height = 54.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEFEFF4))
                    .border(1.dp, BorderGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                ReceiptThumbnailCanvas(
                    modifier = Modifier.size(width = 24.dp, height = 32.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Title & Category Column
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "${item.date} · ${item.category}",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))

                // Receipt Badge (Green ✓ Ada nota)
                if (item.hasReceipt) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "Ada nota",
                            tint = Color(0xFF0F9D58),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Ada nota",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF0F9D58)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Amount Column (Red -Rp75.000)
            Text(
                text = "-${formatRupiah(item.amount)}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDestructive
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun ExpenseListScreenPreview() {
    PanitiaHubTheme {
        ExpenseListScreen(
            expenses = SampleExpenseData.initialExpenses,
            onExpenseClick = {},
            onAddExpenseClick = {},
            onNavigateRoute = {}
        )
    }
}
