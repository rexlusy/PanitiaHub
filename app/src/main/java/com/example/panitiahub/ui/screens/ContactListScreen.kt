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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.panitiahub.data.ContactItem
import com.example.panitiahub.data.SampleContactData
import com.example.panitiahub.ui.components.AppBottomNavigationBar
import com.example.panitiahub.ui.theme.AppBackground
import com.example.panitiahub.ui.theme.BadgeGreenBg
import com.example.panitiahub.ui.theme.BadgeGreenText
import com.example.panitiahub.ui.theme.BadgeOrangeBg
import com.example.panitiahub.ui.theme.BadgeOrangeText
import com.example.panitiahub.ui.theme.BorderGray
import com.example.panitiahub.ui.theme.CardBackground
import com.example.panitiahub.ui.theme.PanitiaHubTheme
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.PrimaryBlueLight
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary
import com.example.panitiahub.ui.theme.UnselectedTabBg
import com.example.panitiahub.ui.theme.UnselectedTabText

@Composable
fun ContactListScreen(
    contacts: List<ContactItem>,
    onContactClick: (ContactItem) -> Unit,
    onAddContactClick: () -> Unit,
    onNavigateRoute: (String) -> Unit,
    isLoading: Boolean = false,
    onRefresh: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var selectedDivision by remember { mutableStateOf("Semua Divisi") }

    val categories = listOf("Semua", "Panitia Pelaksana", "Narasumber", "Juri", "Vendor")

    // Ambil daftar divisi dinamis dari panitia yang ada
    val availableDivisions = listOf("Semua Divisi") + contacts
        .filter { it.contactType == "Panitia Pelaksana" }
        .mapNotNull { it.division }
        .filter { it.isNotBlank() }
        .distinct()

    val filteredContacts = contacts.filter { contact ->
        val matchesCategory = when (selectedCategory) {
            "Semua" -> true
            else -> contact.contactType.equals(selectedCategory, ignoreCase = true)
        }

        val matchesDivision = if (selectedCategory == "Panitia Pelaksana" && selectedDivision != "Semua Divisi") {
            contact.division.equals(selectedDivision, ignoreCase = true)
        } else {
            true
        }

        val matchesSearch = if (searchQuery.isBlank()) {
            true
        } else {
            contact.name.contains(searchQuery, ignoreCase = true) ||
            contact.role.contains(searchQuery, ignoreCase = true) ||
            (contact.division?.contains(searchQuery, ignoreCase = true) == true) ||
            (contact.institution?.contains(searchQuery, ignoreCase = true) == true)
        }

        matchesCategory && matchesDivision && matchesSearch
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppBackground,
        bottomBar = {
            AppBottomNavigationBar(
                selectedRoute = "home",
                onRouteSelected = onNavigateRoute
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddContactClick,
                shape = CircleShape,
                containerColor = PrimaryBlue,
                contentColor = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Kontak",
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

            // Header Title with Refresh Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Direktori Kontak Acara",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Kelola panitia, narasumber, juri, dan vendor",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
                IconButton(onClick = onRefresh) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Muat Ulang",
                        tint = PrimaryBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Cari nama, divisi, instansi, atau role...", fontSize = 13.sp, color = TextSecondary) },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Cari", tint = TextSecondary)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = BorderGray
                ),
                singleLine = true
            )

            if (isLoading) {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = PrimaryBlue,
                    trackColor = PrimaryBlueLight
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Level 1: Category Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { cat ->
                    val isSelected = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) PrimaryBlue else UnselectedTabBg)
                            .clickable {
                                selectedCategory = cat
                                selectedDivision = "Semua Divisi"
                            }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = cat,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) Color.White else UnselectedTabText
                        )
                    }
                }
            }

            // Level 2: Sub-filter Divisi (Khusus jika Panitia Pelaksana aktif)
            if (selectedCategory == "Panitia Pelaksana" && availableDivisions.size > 1) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(availableDivisions) { div ->
                        val isSelected = div == selectedDivision
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) PrimaryBlue.copy(alpha = 0.15f) else Color.Transparent)
                                .clickable { selectedDivision = div }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = div,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) PrimaryBlue else TextSecondary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Contact Items List with Section Grouping
            if (filteredContacts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = PrimaryBlue)
                    } else {
                        Text(
                            text = "Tidak ada kontak pada kategori ini",
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    when (selectedCategory) {
                        "Panitia Pelaksana" -> {
                            val groupedByDivision = filteredContacts.groupBy { it.division ?: "Divisi Umum" }
                            groupedByDivision.forEach { (divisionName, divisionMembers) ->
                                item(key = "header_div_$divisionName") {
                                    SectionHeader(
                                        title = divisionName,
                                        count = divisionMembers.size,
                                        badgeLabel = "Panitia"
                                    )
                                }
                                items(
                                    items = divisionMembers,
                                    key = { it.id }
                                ) { contact ->
                                    ContactCardItem(
                                        contact = contact,
                                        onClick = { onContactClick(contact) }
                                    )
                                }
                            }
                        }
                        "Semua" -> {
                            val groupedByCategory = filteredContacts.groupBy { it.contactType }
                            groupedByCategory.forEach { (categoryName, categoryMembers) ->
                                item(key = "header_cat_$categoryName") {
                                    SectionHeader(
                                        title = categoryName,
                                        count = categoryMembers.size,
                                        badgeLabel = "Orang"
                                    )
                                }
                                items(
                                    items = categoryMembers,
                                    key = { it.id }
                                ) { contact ->
                                    ContactCardItem(
                                        contact = contact,
                                        onClick = { onContactClick(contact) }
                                    )
                                }
                            }
                        }
                        else -> {
                            items(
                                items = filteredContacts,
                                key = { it.id }
                            ) { contact ->
                                ContactCardItem(
                                    contact = contact,
                                    onClick = { onContactClick(contact) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    count: Int,
    badgeLabel: String = "Panitia",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(width = 4.dp, height = 16.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(PrimaryBlue)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(PrimaryBlueLight)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                text = "$count $badgeLabel",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue
            )
        }
    }
}

@Composable
fun ContactCardItem(
    contact: ContactItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPanitia = contact.contactType == "Panitia Pelaksana"
    val badgeBg = when (contact.contactType) {
        "Panitia Pelaksana" -> PrimaryBlueLight
        "Narasumber" -> BadgeGreenBg
        "Juri" -> Color(0xFFEDF2FE)
        else -> BadgeOrangeBg
    }
    val badgeText = when (contact.contactType) {
        "Panitia Pelaksana" -> PrimaryBlue
        "Narasumber" -> BadgeGreenText
        "Juri" -> Color(0xFF3B82F6)
        else -> BadgeOrangeText
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            // Avatar
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(badgeBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = contact.name,
                    tint = badgeText,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = contact.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    // Badge Kategori
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(badgeBg)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = contact.contactType,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = badgeText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Baris kedua: Subtitle (Divisi & Role / Instansi & Role)
                val subtitle = if (isPanitia) {
                    "${contact.division ?: ""} • ${contact.role}"
                } else {
                    "${contact.role} ${if (!contact.institution.isNullOrBlank()) "(${contact.institution})" else ""}"
                }

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = "Telepon",
                        tint = TextSecondary,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = contact.phone,
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun ContactListScreenPreview() {
    PanitiaHubTheme {
        ContactListScreen(
            contacts = SampleContactData.initialContacts,
            onContactClick = {},
            onAddContactClick = {},
            onNavigateRoute = {}
        )
    }
}
