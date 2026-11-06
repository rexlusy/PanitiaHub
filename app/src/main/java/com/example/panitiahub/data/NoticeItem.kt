package com.example.panitiahub.data

import java.util.UUID

data class NoticeItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val senderName: String,
    val senderRole: String,
    val timeAgo: String,
    val priority: String = "Penting"
)

object SampleNoticeData {
    val initialNotices = listOf(
        NoticeItem(
            id = "1",
            title = "Gladi Bersih Dimulai Pukul 16.30 WIB",
            content = "Seluruh koordinator divisi wajib berkumpul di panggung utama tepat waktu membawa HT masing-masing.",
            senderName = "Ketua Panitia",
            senderRole = "Bagas Pratama",
            timeAgo = "10 menit yang lalu",
            priority = "Mendesak"
        ),
        NoticeItem(
            id = "2",
            title = "Pengambilan Konsumsi Malam",
            content = "Konsumsi malam panitia dapat diambil di posko konsumsi ruang B201 mulai pukul 18.30 WIB.",
            senderName = "Sie Konsumsi",
            senderRole = "Siti Aisyah",
            timeAgo = "1 jam yang lalu",
            priority = "Informasi"
        ),
        NoticeItem(
            id = "3",
            title = "Perubahan Rute Evakuasi & Parkir VIP",
            content = "Area parkir VIP dipindahkan ke lapangan barat karena panggung outdoor memerlukan area tambahan.",
            senderName = "Sie Keamanan",
            senderRole = "Rizky Ramadhan",
            timeAgo = "2 jam yang lalu",
            priority = "Penting"
        )
    )
}
