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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.panitiahub.data.SampleTaskData
import com.example.panitiahub.data.TaskItem
import com.example.panitiahub.ui.components.AppBottomNavigationBar
import com.example.panitiahub.ui.theme.AppBackground
import com.example.panitiahub.ui.theme.BadgeSkyBg
import com.example.panitiahub.ui.theme.BadgeSkyText
import com.example.panitiahub.ui.theme.CardBackground
import com.example.panitiahub.ui.theme.PanitiaHubTheme
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary
import com.example.panitiahub.ui.theme.UnselectedTabBg
import com.example.panitiahub.ui.theme.UnselectedTabText

enum class TaskFilterOption {
    ALL, MY_TASKS, PENDING, COMPLETED
}

@Composable
fun TaskListScreen(
    tasks: List<TaskItem>,
    onTaskClick: (TaskItem) -> Unit,
    onToggleTaskCompleted: (TaskItem) -> Unit,
    onAddTaskClick: () -> Unit,
    onNavigateRoute: (String) -> Unit,
    currentUserName: String = "Dimas Aditya",
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf(TaskFilterOption.ALL) }

    val myTasksCount = tasks.count { it.picName.equals(currentUserName, ignoreCase = true) }
    val completedCount = tasks.count { it.isCompleted }
    val totalCount = tasks.size
    val pendingCount = totalCount - completedCount
    val progressPercent = if (totalCount > 0) (completedCount.toFloat() / totalCount) else 0f

    val filteredTasks = when (selectedFilter) {
        TaskFilterOption.ALL -> tasks
        TaskFilterOption.MY_TASKS -> tasks.filter { it.picName.equals(currentUserName, ignoreCase = true) }
        TaskFilterOption.PENDING -> tasks.filter { !it.isCompleted }
        TaskFilterOption.COMPLETED -> tasks.filter { it.isCompleted }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppBackground,
        bottomBar = {
            AppBottomNavigationBar(
                selectedRoute = "tasks",
                onRouteSelected = onNavigateRoute
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                shape = CircleShape,
                containerColor = PrimaryBlue,
                contentColor = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Tugas Panitia",
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

            // Header Title
            Text(
                text = "Manajemen Tugas Panitia",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Tugas jelas, koordinasi tuntas",
                fontSize = 13.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Progress Summary Card
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Progres Pengerjaan Acara",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = "${(progressPercent * 100).toInt()}%",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Progress Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progressPercent)
                                .height(8.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "$completedCount dari $totalCount tugas selesai dikerjakan",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Filter Tabs (LazyRow: Semua, Tugas Saya, Belum Selesai, Selesai)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    FilterTabItem(
                        title = "Semua ($totalCount)",
                        isSelected = selectedFilter == TaskFilterOption.ALL,
                        onClick = { selectedFilter = TaskFilterOption.ALL }
                    )
                }
                item {
                    FilterTabItem(
                        title = "Tugas Saya ($myTasksCount)",
                        isSelected = selectedFilter == TaskFilterOption.MY_TASKS,
                        onClick = { selectedFilter = TaskFilterOption.MY_TASKS }
                    )
                }
                item {
                    FilterTabItem(
                        title = "Belum Selesai ($pendingCount)",
                        isSelected = selectedFilter == TaskFilterOption.PENDING,
                        onClick = { selectedFilter = TaskFilterOption.PENDING }
                    )
                }
                item {
                    FilterTabItem(
                        title = "Selesai ($completedCount)",
                        isSelected = selectedFilter == TaskFilterOption.COMPLETED,
                        onClick = { selectedFilter = TaskFilterOption.COMPLETED }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Task Items List
            if (filteredTasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (selectedFilter == TaskFilterOption.MY_TASKS) {
                            "Belum ada tugas yang ditugaskan ke Anda ($currentUserName)"
                        } else {
                            "Tidak ada tugas pada filter ini"
                        },
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = filteredTasks,
                        key = { it.id }
                    ) { task ->
                        val isAssignedToMe = task.picName.equals(currentUserName, ignoreCase = true)
                        TaskCardItem(
                            task = task,
                            isAssignedToMe = isAssignedToMe,
                            onCardClick = { onTaskClick(task) },
                            onToggleClick = { onToggleTaskCompleted(task) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterTabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) PrimaryBlue else UnselectedTabBg)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) Color.White else UnselectedTabText
        )
    }
}

@Composable
fun TaskCardItem(
    task: TaskItem,
    isAssignedToMe: Boolean = false,
    onCardClick: () -> Unit,
    onToggleClick: () -> Unit,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onToggleClick) {
                Icon(
                    imageVector = if (task.isCompleted) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                    contentDescription = "Toggle Complete",
                    tint = if (task.isCompleted) Color(0xFF0F9D58) else TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = task.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    if (isAssignedToMe) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(BadgeSkyBg)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Tugas Anda",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = BadgeSkyText
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${task.division} • PIC: ${task.picName}",
                    fontSize = 13.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Tenggat: ${task.dueDate}",
                    fontSize = 12.sp,
                    color = PrimaryBlue,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun TaskListScreenPreview() {
    PanitiaHubTheme {
        TaskListScreen(
            tasks = SampleTaskData.initialTasks,
            onTaskClick = {},
            onToggleTaskCompleted = {},
            onAddTaskClick = {},
            onNavigateRoute = {}
        )
    }
}
