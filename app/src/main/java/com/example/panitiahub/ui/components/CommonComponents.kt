package com.example.panitiahub.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panitiahub.data.LogisticsItem
import com.example.panitiahub.ui.theme.BadgeOrangeBg
import com.example.panitiahub.ui.theme.BadgeOrangeText
import com.example.panitiahub.ui.theme.CardBackground
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.PrimaryBlueLight
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary
import com.example.panitiahub.ui.theme.WarningOrangeBg
import com.example.panitiahub.ui.theme.WarningOrangeText

@Composable
fun StatusBadge(
    isReturned: Boolean,
    modifier: Modifier = Modifier
) {
    val text = if (isReturned) "Sudah Kembali" else "Dipinjam"
    val bgColor = if (isReturned) Color(0xFFE6F4EA) else BadgeOrangeBg
    val textColor = if (isReturned) Color(0xFF137333) else BadgeOrangeText

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

@Composable
fun LogisticsIconCanvas(
    iconType: String = "HT",
    modifier: Modifier = Modifier,
    strokeColor: Color = BadgeOrangeText
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.5.dp.toPx()
        val width = size.width
        val height = size.height

        when (iconType.uppercase()) {
            "HT" -> {
                // Antenna on left
                drawLine(
                    color = strokeColor,
                    start = Offset(width * 0.32f, height * 0.12f),
                    end = Offset(width * 0.32f, height * 0.38f),
                    strokeWidth = strokeWidth
                )
                // Dial/knob on right
                drawRoundRect(
                    color = strokeColor,
                    topLeft = Offset(width * 0.62f, height * 0.28f),
                    size = Size(width * 0.12f, height * 0.1f),
                    cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
                    style = Stroke(width = strokeWidth)
                )
                // Main body
                val bodyTop = height * 0.38f
                drawRoundRect(
                    color = strokeColor,
                    topLeft = Offset(width * 0.2f, bodyTop),
                    size = Size(width * 0.6f, height * 0.52f),
                    cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx()),
                    style = Stroke(width = strokeWidth)
                )
                // Screen rectangle inside body
                drawRoundRect(
                    color = strokeColor,
                    topLeft = Offset(width * 0.32f, bodyTop + height * 0.1f),
                    size = Size(width * 0.36f, height * 0.16f),
                    cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
                    style = Stroke(width = strokeWidth)
                )
                // Speaker grille lines inside screen
                drawLine(
                    color = strokeColor,
                    start = Offset(width * 0.38f, bodyTop + height * 0.18f),
                    end = Offset(width * 0.62f, bodyTop + height * 0.18f),
                    strokeWidth = strokeWidth * 0.8f
                )
                // Speaker/Button lines below screen
                drawLine(
                    color = strokeColor,
                    start = Offset(width * 0.32f, bodyTop + height * 0.32f),
                    end = Offset(width * 0.68f, bodyTop + height * 0.32f),
                    strokeWidth = strokeWidth * 0.8f
                )
                drawLine(
                    color = strokeColor,
                    start = Offset(width * 0.32f, bodyTop + height * 0.40f),
                    end = Offset(width * 0.68f, bodyTop + height * 0.40f),
                    strokeWidth = strokeWidth * 0.8f
                )
            }
            "KABEL" -> {
                // Cable reel circle
                drawCircle(
                    color = strokeColor,
                    radius = width * 0.32f,
                    center = Offset(width * 0.5f, height * 0.52f),
                    style = Stroke(width = strokeWidth)
                )
                drawCircle(
                    color = strokeColor,
                    radius = width * 0.16f,
                    center = Offset(width * 0.5f, height * 0.52f),
                    style = Stroke(width = strokeWidth)
                )
                // Top handle
                drawLine(
                    color = strokeColor,
                    start = Offset(width * 0.35f, height * 0.18f),
                    end = Offset(width * 0.65f, height * 0.18f),
                    strokeWidth = strokeWidth
                )
                drawLine(
                    color = strokeColor,
                    start = Offset(width * 0.35f, height * 0.18f),
                    end = Offset(width * 0.35f, height * 0.25f),
                    strokeWidth = strokeWidth
                )
                drawLine(
                    color = strokeColor,
                    start = Offset(width * 0.65f, height * 0.18f),
                    end = Offset(width * 0.65f, height * 0.25f),
                    strokeWidth = strokeWidth
                )
            }
            else -> { // "POINTER" / Default
                // Remote control body
                drawRoundRect(
                    color = strokeColor,
                    topLeft = Offset(width * 0.32f, height * 0.18f),
                    size = Size(width * 0.36f, height * 0.64f),
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                    style = Stroke(width = strokeWidth)
                )
                // Top laser indicator point
                drawCircle(
                    color = strokeColor,
                    radius = width * 0.04f,
                    center = Offset(width * 0.5f, height * 0.30f),
                    style = Stroke(width = strokeWidth)
                )
                // Direction / Next-Previous buttons
                drawCircle(
                    color = strokeColor,
                    radius = width * 0.08f,
                    center = Offset(width * 0.5f, height * 0.48f),
                    style = Stroke(width = strokeWidth)
                )
                drawCircle(
                    color = strokeColor,
                    radius = width * 0.06f,
                    center = Offset(width * 0.5f, height * 0.68f),
                    style = Stroke(width = strokeWidth)
                )
            }
        }
    }
}

@Composable
fun LogisticsIconContainer(
    iconType: String = "HT",
    containerSize: Dp = 48.dp,
    iconSize: Dp = 28.dp,
    backgroundColor: Color = BadgeOrangeBg,
    strokeColor: Color = BadgeOrangeText
) {
    Box(
        modifier = Modifier
            .size(containerSize)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        LogisticsIconCanvas(
            iconType = iconType,
            modifier = Modifier.size(iconSize),
            strokeColor = strokeColor
        )
    }
}

@Composable
fun LogisticsItemCard(
    item: LogisticsItem,
    onCardClick: () -> Unit,
    onReturnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Icon + Name & Code
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LogisticsIconContainer(
                    iconType = item.iconType,
                    containerSize = 48.dp,
                    iconSize = 28.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${item.code} · ${item.quantity} ${item.unitLabel}",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Borrower Details Row
            Text(
                text = "${item.borrowerName} · ${item.division}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Date & Time
            Text(
                text = item.borrowDateTime,
                fontSize = 13.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Bottom Actions Row: Status Badge + Return Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(isReturned = item.isReturned)

                if (!item.isReturned) {
                    OutlinedButton(
                        onClick = onReturnClick,
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 16.dp,
                            vertical = 6.dp
                        )
                    ) {
                        Text(
                            text = "Kembalikan",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppBottomNavigationBar(
    selectedRoute: String = "logistics",
    onRouteSelected: (String) -> Unit = {}
) {
    NavigationBar(
        containerColor = CardBackground,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple("home", "Home", Icons.Outlined.Home),
            Triple("tasks", "Tasks", Icons.AutoMirrored.Outlined.Assignment),
            Triple("logistics", "Logistics", Icons.Outlined.Inventory2),
            Triple("finance", "Finance", Icons.Outlined.AccountBalanceWallet)
        )

        items.forEach { (route, label, icon) ->
            val isSelected = selectedRoute == route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onRouteSelected(route) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (isSelected) PrimaryBlue else TextSecondary
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) PrimaryBlue else TextSecondary
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = PrimaryBlueLight,
                    selectedIconColor = PrimaryBlue,
                    selectedTextColor = PrimaryBlue,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}

@Composable
fun WarningAlertBox(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(WarningOrangeBg)
            .padding(16.dp)
    ) {
        Text(
            text = message,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = WarningOrangeText,
            lineHeight = 18.sp
        )
    }
}
