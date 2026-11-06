package com.example.panitiahub.data

import java.util.UUID

data class ContactItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val division: String,
    val role: String,
    val phone: String,
    val email: String = "panitia@panitiahub.id",
    val isVip: Boolean = false
)

object SampleContactData {
    val initialContacts = listOf(
        ContactItem("1", "Dimas Aditya", "Sie Acara", "Koordinator", "0812 3456 7890", "dimas@panitiahub.id"),
        ContactItem("2", "Nadia Putri", "Sie Perlengkapan", "Koordinator", "0813 9876 5432", "nadia@panitiahub.id"),
        ContactItem("3", "Hania Putri", "Sie Acara", "Anggota", "0815 1122 3344", "hania@panitiahub.id"),
        ContactItem("4", "Rizky Ramadhan", "Sie Keamanan", "Koordinator", "0812 2233 4455", "rizky@panitiahub.id"),
        ContactItem("5", "Dr. Ir. Budi Santoso", "Tamu VIP", "Pembicara Utama", "0811 5566 7788", "budi@univ.ac.id", isVip = true)
    )
}
