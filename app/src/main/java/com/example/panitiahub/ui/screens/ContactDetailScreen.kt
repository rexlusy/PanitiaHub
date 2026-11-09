package com.example.panitiahub.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panitiahub.data.ContactItem
import com.example.panitiahub.data.SampleContactData
import com.example.panitiahub.ui.theme.AppBackground
import com.example.panitiahub.ui.theme.BadgeGreenBg
import com.example.panitiahub.ui.theme.BadgeGreenText
import com.example.panitiahub.ui.theme.BadgeOrangeBg
import com.example.panitiahub.ui.theme.BadgeOrangeText
import com.example.panitiahub.ui.theme.CardBackground
import com.example.panitiahub.ui.theme.PanitiaHubTheme
import com.example.panitiahub.ui.theme.PrimaryBlue
import com.example.panitiahub.ui.theme.PrimaryBlueLight
import com.example.panitiahub.ui.theme.TextPrimary
import com.example.panitiahub.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    contact: ContactItem,
    onBackClick: () -> Unit,
    onEditClick: (ContactItem) -> Unit = {},
    onDeleteClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

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

    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = {
                Text(
                    text = "Hapus Kontak?",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            text = {
                Text(
                    text = "Apakah Anda yakin ingin menghapus ${contact.name} dari daftar kontak acara? Tindakan ini tidak dapat dibatalkan.",
                    color = TextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirmDialog = false
                        onDeleteClick(contact.id)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                ) {
                    Text("Hapus", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = false }) {
                    Text("Batal", color = TextSecondary)
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail Kontak",
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
                actions = {
                    IconButton(onClick = { onEditClick(contact) }) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit Kontak",
                            tint = PrimaryBlue
                        )
                    }
                    IconButton(onClick = { showDeleteConfirmDialog = true }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Hapus Kontak",
                            tint = Color(0xFFEF4444)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Header Box
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(badgeBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = contact.name,
                    tint = badgeText,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = contact.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Badge Kategori Utama
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(badgeBg)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = contact.contactType,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = badgeText
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (isPanitia) {
                    "${contact.division ?: ""} • ${contact.role}"
                } else {
                    "${contact.role} ${if (!contact.institution.isNullOrBlank()) "(${contact.institution})" else ""}"
                },
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Contact Info Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    ContactDetailRow(
                        icon = Icons.Outlined.Phone,
                        label = "Nomor Telepon / WhatsApp",
                        value = contact.phone
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ContactDetailRow(
                        icon = Icons.Outlined.Email,
                        label = "Email Kontak",
                        value = contact.email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isPanitia) {
                        ContactDetailRow(
                            icon = Icons.Outlined.WorkOutline,
                            label = "Divisi Kepanitiaan",
                            value = contact.division ?: "-"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        ContactDetailRow(
                            icon = Icons.Outlined.Person,
                            label = "Jabatan / Role",
                            value = contact.role
                        )
                    } else {
                        if (!contact.institution.isNullOrBlank()) {
                            ContactDetailRow(
                                icon = Icons.Outlined.Business,
                                label = "Asal Institusi / Perusahaan",
                                value = contact.institution
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        ContactDetailRow(
                            icon = Icons.Outlined.WorkOutline,
                            label = "Tugas / Peran Acara",
                            value = contact.role
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Outlined.Phone, contentDescription = "Hubungi")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Hubungi via WhatsApp", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { onEditClick(contact) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue)
            ) {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Edit Data Kontak", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { showDeleteConfirmDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFEF4444)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444))
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Hapus")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Hapus Kontak Ini", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ContactDetailRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = TextSecondary,
            modifier = Modifier
                .size(22.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
fun ContactDetailScreenPreview() {
    PanitiaHubTheme {
        ContactDetailScreen(
            contact = SampleContactData.initialContacts[0],
            onBackClick = {}
        )
    }
}
