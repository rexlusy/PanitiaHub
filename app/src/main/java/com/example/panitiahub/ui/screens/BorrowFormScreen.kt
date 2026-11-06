package com.example.panitiahub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panitiahub.data.LogisticsItem
import com.example.panitiahub.ui.components.WarningAlertBox
import com.example.panitiahub.ui.theme.AppBackground
import com.example.panitiahub.ui.theme.PanitiaHubTheme
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrowFormScreen(
    onBackClick: () -> Unit,
    onSubmit: (LogisticsItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedItemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("1") }
    var borrowerName by remember { mutableStateOf("") }
    var selectedDivision by remember { mutableStateOf("") }
    var borrowDateTime by remember { mutableStateOf("03 November 2026 · 08.15") }

    var isItemDropdownExpanded by remember { mutableStateOf(false) }
    var isDivisionDropdownExpanded by remember { mutableStateOf(false) }

    val itemOptions = listOf(
        "HT Motorola 04" to "HT-004",
        "Kabel Roll 25 Meter" to "KBL-002",
        "Pointer Presentasi" to "PTR-001",
        "Megaphone Toa" to "MGP-001",
        "Projector Epson" to "PRJ-003"
    )

    val divisionOptions = listOf(
        "Sie Acara",
        "Sie Perlengkapan",
        "Sie HPD",
        "Sie Humas",
        "Sie Konsumsi",
        "Sie Keamanan",
        "Sie Transkop"
    )

    val textFieldShape = RoundedCornerShape(14.dp)
    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color(0xFFF9FAFC),
        unfocusedContainerColor = Color(0xFFF9FAFC),
        disabledContainerColor = Color(0xFFF9FAFC),
        focusedBorderColor = PrimaryBlue,
        unfocusedBorderColor = Color(0xFFE5E7EB),
        focusedLabelColor = TextPrimary,
        unfocusedLabelColor = TextPrimary
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Form Pinjam Logistik",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Kembali",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppBackground)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = {
                        val nameToUse = selectedItemName.ifEmpty { "HT Motorola 04" }
                        val codeToUse = itemOptions.find { it.first == nameToUse }?.second ?: "HT-004"
                        val borrowerToUse = borrowerName.ifEmpty { "Dimas Aditya" }
                        val divisionToUse = selectedDivision.ifEmpty { "Sie Acara" }
                        val qtyToUse = itemQuantity.toIntOrNull() ?: 1

                        val iconTypeToUse = when {
                            nameToUse.contains("Kabel", ignoreCase = true) -> "KABEL"
                            nameToUse.contains("Pointer", ignoreCase = true) -> "POINTER"
                            else -> "HT"
                        }

                        val newItem = LogisticsItem(
                            name = nameToUse,
                            code = codeToUse,
                            quantity = qtyToUse,
                            borrowerName = borrowerToUse,
                            division = divisionToUse,
                            contactNumber = "0812 3456 7890",
                            borrowDateTime = if (borrowDateTime.endsWith("WIB")) borrowDateTime else "$borrowDateTime WIB",
                            returnDateTime = "06 November 2026 · 18.00 WIB",
                            isReturned = false,
                            iconType = iconTypeToUse
                        )
                        onSubmit(newItem)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Simpan & Konfirmasi Peminjaman",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Form Title & Subtitle
            Text(
                text = "Catat alat sebelum dibawa",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Satu catatan untuk satu jenis barang.",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Field 1: Nama Barang / Alat (Dropdown)
            Text(
                text = "Nama Barang / Alat",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box {
                OutlinedTextField(
                    value = selectedItemName,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = "Pilih HT, kabel roll, pointer...",
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = "Pilih Barang",
                            tint = TextSecondary,
                            modifier = Modifier.clickable { isItemDropdownExpanded = true }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isItemDropdownExpanded = true },
                    shape = textFieldShape,
                    colors = customTextFieldColors,
                    singleLine = true
                )

                DropdownMenu(
                    expanded = isItemDropdownExpanded,
                    onDismissRequest = { isItemDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    itemOptions.forEach { (itemName, _) ->
                        DropdownMenuItem(
                            text = { Text(text = itemName, fontSize = 14.sp) },
                            onClick = {
                                selectedItemName = itemName
                                isItemDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Field 2: Jumlah Unit
            Text(
                text = "Jumlah Unit",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = itemQuantity,
                onValueChange = { itemQuantity = it },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Field 3: Nama Peminjam
            Text(
                text = "Nama Peminjam",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = borrowerName,
                onValueChange = { borrowerName = it },
                placeholder = {
                    Text(
                        text = "Nama lengkap panitia",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Field 4: Divisi (Dropdown)
            Text(
                text = "Divisi",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box {
                OutlinedTextField(
                    value = selectedDivision,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = "Pilih divisi peminjam",
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = "Pilih Divisi",
                            tint = TextSecondary,
                            modifier = Modifier.clickable { isDivisionDropdownExpanded = true }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDivisionDropdownExpanded = true },
                    shape = textFieldShape,
                    colors = customTextFieldColors,
                    singleLine = true
                )

                DropdownMenu(
                    expanded = isDivisionDropdownExpanded,
                    onDismissRequest = { isDivisionDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    divisionOptions.forEach { division ->
                        DropdownMenuItem(
                            text = { Text(text = division, fontSize = 14.sp) },
                            onClick = {
                                selectedDivision = division
                                isDivisionDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Field 5: Tanggal & Jam Pinjam
            Text(
                text = "Tanggal & Jam Pinjam",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = borrowDateTime,
                onValueChange = { borrowDateTime = it },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = "Tanggal & Jam",
                        tint = TextSecondary
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Warning Alert Box
            WarningAlertBox(
                message = "Cek kondisi barang bersama peminjam sebelum diserahkan."
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun BorrowFormScreenPreview() {
    PanitiaHubTheme {
        BorrowFormScreen(
            onBackClick = {},
            onSubmit = {}
        )
    }
}
