package com.example.panitiahub.data

import java.util.UUID

data class TaskItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val division: String,
    val picName: String,
    val dueDate: String,
    val isCompleted: Boolean = false,
    val description: String = "Pastikan semua perlengkapan dan teknis siap sebelum gladi bersih."
)

object SampleTaskData {
    val initialTasks = listOf(
        TaskItem("1", "Checklist Sound System & HT", "Sie Perlengkapan", "Nadia Putri", "03 Nov · 15.00 WIB", isCompleted = false),
        TaskItem("2", "Briefing Pengisi Acara & MC", "Sie Acara", "Dimas Aditya", "03 Nov · 16.30 WIB", isCompleted = false),
        TaskItem("3", "Cetak & Pasang Banner Panggung", "Sie HPD", "Hania Putri", "02 Nov · 20.00 WIB", isCompleted = true),
        TaskItem("4", "Sterilisasi Area Utama & Parkir", "Sie Keamanan", "Rizky Ramadhan", "03 Nov · 17.00 WIB", isCompleted = false),
        TaskItem("5", "Distribusi Konsumsi Panitia H-1", "Sie Konsumsi", "Siti Aisyah", "02 Nov · 12.00 WIB", isCompleted = true)
    )
}
