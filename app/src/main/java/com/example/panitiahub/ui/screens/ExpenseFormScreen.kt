package com.example.panitiahub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panitiahub.data.ExpenseItem
import com.example.panitiahub.ui.theme.AppBackground
import com.example.panitiahub.ui.theme.BorderGray
import com.example.panitiahub.ui.theme.PanitiaHubTheme
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.PrimaryBlueLight
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseFormScreen(
    initialItem: ExpenseItem? = null,
    onBackClick: () -> Unit,
    onSubmit: (ExpenseItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(initialItem?.title ?: "Beli Baterai Mic & HT") }
    var selectedCategory by remember { mutableStateOf(initialItem?.category ?: "Perlengkapan") }
    var amountText by remember { mutableStateOf(if (initialItem != null) "Rp ${initialItem.amount}" else "Rp 75.000") }
    var dateText by remember { mutableStateOf("03 November 2026") }
    var selectedFileName by remember { mutableStateOf<String?>(initialItem?.receiptFileName ?: "nota_baterai_03nov.jpg") }

    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }

    val categoryOptions = listOf(
        "Perlengkapan",
        "Konsumsi",
        "PDD",
        "Transportasi",
        "Lain-lain"
    )

    val textFieldShape = RoundedCornerShape(14.dp)
    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color(0xFFF9FAFC),
        unfocusedContainerColor = Color(0xFFF9FAFC),
        focusedBorderColor = PrimaryBlue,
        unfocusedBorderColor = BorderGray,
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
                        text = "Catat Pengeluaran",
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
                        val cleanedAmount = amountText.replace(Regex("[^0-9]"), "").toLongOrNull() ?: 75000L
                        val titleToUse = title.ifEmpty { "Beli Baterai Mic & HT" }
                        val categoryToUse = selectedCategory.ifEmpty { "Perlengkapan" }

                        val savedItem = ExpenseItem(
                            id = initialItem?.id ?: java.util.UUID.randomUUID().toString(),
                            title = titleToUse,
                            amount = cleanedAmount,
                            category = categoryToUse,
                            date = "03 Nov",
                            hasReceipt = selectedFileName != null,
                            receiptFileName = selectedFileName ?: "nota_baterai_03nov.jpg",
                            dateTimeFull = "03 November 2026 · 10.30 WIB",
                            picName = "Dimas Aditya"
                        )
                        onSubmit(savedItem)
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
                        text = "Simpan Pengeluaran & Upload Struk",
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

            // Main Heading
            Text(
                text = "Belanja apa hari ini?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Field 1: Judul Pengeluaran
            Text(
                text = "Judul Pengeluaran",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Field 2: Kategori Dropdown
            Text(
                text = "Kategori",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = "Pilih Kategori",
                            tint = TextSecondary,
                            modifier = Modifier.clickable { isCategoryDropdownExpanded = true }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isCategoryDropdownExpanded = true },
                    shape = textFieldShape,
                    colors = customTextFieldColors,
                    singleLine = true
                )

                DropdownMenu(
                    expanded = isCategoryDropdownExpanded,
                    onDismissRequest = { isCategoryDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    categoryOptions.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(text = category, fontSize = 14.sp) },
                            onClick = {
                                selectedCategory = category
                                isCategoryDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Field 3: Nominal Rupiah
            Text(
                text = "Nominal Rupiah",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Field 4: Tanggal
            Text(
                text = "Tanggal",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = dateText,
                onValueChange = { dateText = it },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = "Tanggal",
                        tint = TextSecondary
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Dashed Bordered Upload Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PrimaryBlueLight)
                    .border(1.5.dp, PrimaryBlue, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.UploadFile,
                            contentDescription = "Upload",
                            tint = PrimaryBlue,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Unggah Foto Struk / Nota Fisik",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryBlue
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Buttons Kamera & Galeri
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { selectedFileName = "nota_baterai_03nov.jpg" },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue)
                        ) {
                            Text(text = "Kamera", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }

                        OutlinedButton(
                            onClick = { selectedFileName = "nota_baterai_03nov.jpg" },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue)
                        ) {
                            Text(text = "Galeri", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    // Selected File Preview
                    if (selectedFileName != null) {
                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .padding(12.dp)
                        ) {
                            ReceiptThumbnailCanvas(
                                modifier = Modifier.size(width = 24.dp, height = 32.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = selectedFileName!!,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "248 KB · Foto siap diunggah",
                                    fontSize = 12.sp,
                                    color = Color(0xFF0F9D58),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun ExpenseFormScreenPreview() {
    PanitiaHubTheme {
        ExpenseFormScreen(
            onBackClick = {},
            onSubmit = {}
        )
    }
}
