package com.example.panitiahub.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panitiahub.data.ContactItem
import com.example.panitiahub.ui.theme.AppBackground
import com.example.panitiahub.ui.theme.BorderGray
import com.example.panitiahub.ui.theme.PanitiaHubTheme
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary
import com.example.panitiahub.ui.theme.UnselectedTabBg
import com.example.panitiahub.ui.theme.UnselectedTabText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactFormScreen(
    onBackClick: () -> Unit,
    onSubmit: (ContactItem) -> Unit,
    contactToEdit: ContactItem? = null,
    existingDivisions: List<String> = listOf("Sie Acara", "Sie Perlengkapan", "Sie Keamanan", "Sie Konsumsi", "Sie Humas"),
    modifier: Modifier = Modifier
) {
    val categories = listOf("Panitia Pelaksana", "Narasumber", "Juri", "Vendor")
    var selectedCategory by remember { mutableStateOf(contactToEdit?.contactType ?: "Panitia Pelaksana") }

    var name by remember { mutableStateOf(contactToEdit?.name ?: "") }
    var selectedDivision by remember {
        mutableStateOf(contactToEdit?.division ?: existingDivisions.firstOrNull() ?: "Sie Acara")
    }
    var isCustomDivision by remember { mutableStateOf(false) }
    var customDivisionName by remember { mutableStateOf("") }
    var isDivisionDropdownExpanded by remember { mutableStateOf(false) }

    var role by remember { mutableStateOf(contactToEdit?.role ?: "") }
    var institution by remember { mutableStateOf(contactToEdit?.institution ?: "") }
    var phone by remember { mutableStateOf(contactToEdit?.phone ?: "") }
    var email by remember { mutableStateOf(contactToEdit?.email ?: "") }

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
                        text = if (contactToEdit != null) "Edit Data Kontak" else "Tambah Kontak Baru",
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
                        val nameToUse = name.ifEmpty { "Kontak Baru" }
                        val roleToUse = role.ifEmpty {
                            when (selectedCategory) {
                                "Panitia Pelaksana" -> "Anggota Divisi"
                                "Narasumber" -> "Pemateri"
                                "Juri" -> "Juri Penilai"
                                else -> "Penanggung Jawab Vendor"
                            }
                        }
                        val divisionToUse = if (selectedCategory == "Panitia Pelaksana") {
                            if (isCustomDivision) {
                                customDivisionName.ifEmpty { "Divisi Tambahan" }
                            } else {
                                selectedDivision
                            }
                        } else null

                        val phoneToUse = phone.ifEmpty { "0812 0000 0000" }
                        val emailToUse = email.ifEmpty { "kontak@panitiahub.id" }

                        val contact = ContactItem(
                            id = contactToEdit?.id ?: java.util.UUID.randomUUID().toString(),
                            contactType = selectedCategory,
                            name = nameToUse,
                            division = divisionToUse,
                            role = roleToUse,
                            institution = if (selectedCategory != "Panitia Pelaksana") institution.ifEmpty { null } else null,
                            phone = phoneToUse,
                            email = emailToUse
                        )
                        onSubmit(contact)
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
                        text = if (contactToEdit != null) "Simpan Perubahan" else "Simpan Kontak",
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

            // Kategori Kontak Chips
            Text(
                text = "Kategori Kontak Acara",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.take(2).forEach { category ->
                        val isSelected = selectedCategory == category
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) PrimaryBlue else UnselectedTabBg)
                                .clickable { selectedCategory = category }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = category,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else UnselectedTabText
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.drop(2).forEach { category ->
                        val isSelected = selectedCategory == category
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) PrimaryBlue else UnselectedTabBg)
                                .clickable { selectedCategory = category }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = category,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else UnselectedTabText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Nama Lengkap
            Text(text = "Nama Lengkap", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Contoh: Dimas Aditya", fontSize = 14.sp, color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Form Spesifik Berdasarkan Kategori
            if (selectedCategory == "Panitia Pelaksana") {
                // Dropdown Divisi Dinamis + Tambah Lainnya
                Text(text = "Divisi Kepanitiaan", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(modifier = Modifier.height(6.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = if (isCustomDivision) "+ Tambah Divisi Lainnya..." else selectedDivision,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { isDivisionDropdownExpanded = !isDivisionDropdownExpanded }) {
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Pilih Divisi")
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
                        onDismissRequest = { isDivisionDropdownExpanded = false }
                    ) {
                        existingDivisions.forEach { div ->
                            DropdownMenuItem(
                                text = { Text(div, fontSize = 14.sp) },
                                onClick = {
                                    selectedDivision = div
                                    isCustomDivision = false
                                    isDivisionDropdownExpanded = false
                                }
                            )
                        }
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        tint = PrimaryBlue,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("+ Tambah Divisi Baru...", color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                            },
                            onClick = {
                                isCustomDivision = true
                                isDivisionDropdownExpanded = false
                            }
                        )
                    }
                }

                if (isCustomDivision) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = customDivisionName,
                        onValueChange = { customDivisionName = it },
                        placeholder = { Text("Ketik nama divisi baru...", fontSize = 14.sp, color = TextSecondary) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = textFieldShape,
                        colors = customTextFieldColors,
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                // Untuk Non-Panitia: Field Asal Instansi / Perusahaan
                Text(
                    text = when (selectedCategory) {
                        "Vendor" -> "Nama Perusahaan / Usaha"
                        else -> "Asal Institusi / Universitas / Industri"
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = institution,
                    onValueChange = { institution = it },
                    placeholder = {
                        Text(
                            when (selectedCategory) {
                                "Vendor" -> "Contoh: CV Nada Production"
                                "Narasumber" -> "Contoh: Universitas Brawijaya"
                                else -> "Contoh: Google Developer Expert"
                            },
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
            }

            // Jabatan / Peran / Role
            Text(
                text = when (selectedCategory) {
                    "Panitia Pelaksana" -> "Jabatan / Role (misal: Koordinator, Staff)"
                    "Narasumber" -> "Topik / Peran (misal: Keynote Speaker AI)"
                    "Juri" -> "Bidang Penilaian (misal: Juri UI/UX)"
                    else -> "Layanan / PIC (misal: Sewa Sound System & Panggung)"
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = role,
                onValueChange = { role = it },
                placeholder = {
                    Text(
                        when (selectedCategory) {
                            "Panitia Pelaksana" -> "Contoh: Koordinator Lapangan"
                            "Narasumber" -> "Contoh: Pembicara Sesi 1"
                            "Juri" -> "Contoh: Juri Penilaian Final"
                            else -> "Contoh: PIC Audio"
                        },
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

            // Nomor Telepon / WA
            Text(text = "Nomor Telepon / WhatsApp", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = { Text("0812 3456 7890", fontSize = 14.sp, color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            Text(text = "Email (Opsional)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("kontak@panitiahub.id", fontSize = 14.sp, color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = textFieldShape,
                colors = customTextFieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun ContactFormScreenPreview() {
    PanitiaHubTheme {
        ContactFormScreen(
            onBackClick = {},
            onSubmit = {}
        )
    }
}
