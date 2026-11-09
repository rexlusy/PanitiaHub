package com.example.panitiahub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panitiahub.data.TaskItem
import com.example.panitiahub.ui.theme.AppBackground
import com.example.panitiahub.ui.theme.BorderGray
import com.example.panitiahub.ui.theme.PanitiaHubTheme
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    onBackClick: () -> Unit,
    onSubmit: (TaskItem) -> Unit,
    availableCommitteeMembers: List<String> = listOf("Dimas Aditya", "Nadia Putri", "Hania Putri", "Rizky Ramadhan"),
    availableDivisions: List<String> = listOf("Sie Acara", "Sie Perlengkapan", "Sie Keamanan", "Sie Konsumsi", "Sie Humas"),
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var selectedDivision by remember { mutableStateOf(availableDivisions.firstOrNull() ?: "Sie Acara") }
    var isDivisionDropdownExpanded by remember { mutableStateOf(false) }

    var selectedPic by remember { mutableStateOf(availableCommitteeMembers.firstOrNull() ?: "Dimas Aditya") }
    var isCustomPic by remember { mutableStateOf(false) }
    var customPicName by remember { mutableStateOf("") }
    var isPicDropdownExpanded by remember { mutableStateOf(false) }

    var dueDate by remember { mutableStateOf("03 Nov • 18.00 WIB") }
    var description by remember { mutableStateOf("") }

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
                        text = "Tambah Tugas Panitia",
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
                        val titleToUse = title.ifEmpty { "Tugas Baru" }
                        val divToUse = selectedDivision.ifEmpty { "Sie Acara" }
                        val picToUse = if (isCustomPic) {
                            customPicName.ifEmpty { "Panitia Lapangan" }
                        } else {
                            selectedPic
                        }
                        val descToUse = description.ifEmpty { "Pastikan tugas ini dikoordinasikan dengan baik." }

                        val newTask = TaskItem(
                            title = titleToUse,
                            division = divToUse,
                            picName = picToUse,
                            dueDate = dueDate,
                            isCompleted = false,
                            description = descToUse
                        )
                        onSubmit(newTask)
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
                        text = "Simpan Tugas",
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

            Text(
                text = "Rincian Tugas & Penanggung Jawab",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Tugas akan otomatis terhubung ke PIC dan modul Kontak.",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Judul Tugas
            Text(text = "Nama / Judul Tugas", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Contoh: Checklist Sound System & HT", fontSize = 14.sp, color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Divisi Penanggung Jawab (Dropdown)
            Text(text = "Divisi Penanggung Jawab", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedDivision,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { isDivisionDropdownExpanded = !isDivisionDropdownExpanded }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Pilih Divisi")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDivisionDropdownExpanded = true },
                    shape = textFieldShape,
                    colors = customTextFieldColors
                )

                DropdownMenu(
                    expanded = isDivisionDropdownExpanded,
                    onDismissRequest = { isDivisionDropdownExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(Color.White)
                ) {
                    availableDivisions.forEach { div ->
                        DropdownMenuItem(
                            text = { Text(div, fontSize = 14.sp) },
                            onClick = {
                                selectedDivision = div
                                isDivisionDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // PIC Panitia (Dropdown dari Modul Kontak)
            Text(text = "PIC / Penanggung Jawab (Dari Direktori Kontak)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = if (isCustomPic) "Ketik PIC Lainnya..." else selectedPic,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { isPicDropdownExpanded = !isPicDropdownExpanded }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Pilih PIC")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isPicDropdownExpanded = true },
                    shape = textFieldShape,
                    colors = customTextFieldColors
                )

                DropdownMenu(
                    expanded = isPicDropdownExpanded,
                    onDismissRequest = { isPicDropdownExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(Color.White)
                ) {
                    availableCommitteeMembers.distinct().forEach { name ->
                        DropdownMenuItem(
                            text = { Text(name, fontSize = 14.sp) },
                            onClick = {
                                selectedPic = name
                                isCustomPic = false
                                isPicDropdownExpanded = false
                            }
                        )
                    }

                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.size(8.dp))
                                Text("+ Ketik PIC Lainnya...", fontSize = 14.sp, color = PrimaryBlue, fontWeight = FontWeight.SemiBold)
                            }
                        },
                        onClick = {
                            isCustomPic = true
                            isPicDropdownExpanded = false
                        }
                    )
                }
            }

            if (isCustomPic) {
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = customPicName,
                    onValueChange = { customPicName = it },
                    placeholder = { Text("Ketik nama PIC penanggung jawab", fontSize = 14.sp, color = TextSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = textFieldShape,
                    colors = customTextFieldColors,
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tenggat Waktu
            Text(text = "Tenggat Waktu / Deadline", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = dueDate,
                onValueChange = { dueDate = it },
                placeholder = { Text("Contoh: 03 Nov • 18.00 WIB", fontSize = 14.sp, color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Deskripsi Instruksi
            Text(text = "Catatan & Deskripsi Tugas", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Rincian instruksi yang harus dipersiapkan...", fontSize = 14.sp, color = TextSecondary) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                shape = textFieldShape,
                colors = customTextFieldColors,
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun TaskFormScreenPreview() {
    PanitiaHubTheme {
        TaskFormScreen(
            onBackClick = {},
            onSubmit = {}
        )
    }
}
