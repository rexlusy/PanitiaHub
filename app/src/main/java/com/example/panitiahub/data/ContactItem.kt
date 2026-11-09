package com.example.panitiahub.data

import java.util.UUID

data class ContactItem(
    val id: String = UUID.randomUUID().toString(),
    val contactType: String = "Panitia Pelaksana", // Panitia Pelaksana, Narasumber, Juri, Vendor
    val name: String,
    val division: String? = null, // Khusus Panitia Pelaksana
    val role: String, // Misal: Koordinator, Pemateri AI, Juri Desain, PIC Sound System
    val institution: String? = null, // Asal instansi / perusahaan untuk narasumber, juri, vendor
    val phone: String,
    val email: String = "kontak@panitiahub.id"
)

object SampleContactData {
    val initialContacts = listOf(
        ContactItem(
            id = "1",
            contactType = "Panitia Pelaksana",
            name = "Dimas Aditya",
            division = "Sie Acara",
            role = "Koordinator Divisi",
            phone = "0812 3456 7890",
            email = "dimas@panitiahub.id"
        ),
        ContactItem(
            id = "2",
            contactType = "Panitia Pelaksana",
            name = "Nadia Putri",
            division = "Sie Perlengkapan",
            role = "Staff Logistik",
            phone = "0813 9876 5432",
            email = "nadia@panitiahub.id"
        ),
        ContactItem(
            id = "3",
            contactType = "Panitia Pelaksana",
            name = "Hania Putri",
            division = "Sie Acara",
            role = "Anggota",
            phone = "0815 1122 3344",
            email = "hania@panitiahub.id"
        ),
        ContactItem(
            id = "4",
            contactType = "Narasumber",
            name = "Dr. Ir. Budi Santoso",
            role = "Keynote Speaker AI",
            institution = "Universitas Brawijaya",
            phone = "0811 5566 7788",
            email = "budi@univ.ac.id"
        ),
        ContactItem(
            id = "5",
            contactType = "Juri",
            name = "Sarah Wijaya, M.Ds",
            role = "Juri Lomba UI/UX",
            institution = "Praktisi Industri",
            phone = "0812 8899 0011",
            email = "sarah@design.id"
        ),
        ContactItem(
            id = "6",
            contactType = "Vendor",
            name = "Agus Sound System",
            role = "PIC Audio & Panggung",
            institution = "CV Nada Production",
            phone = "0856 1122 3344",
            email = "sales@nadaproduction.com"
        )
    )
}
