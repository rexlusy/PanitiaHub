package com.example.panitiahub.data

import java.util.UUID

data class LogisticsItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val code: String,
    val quantity: Int = 1,
    val unitLabel: String = "unit",
    val borrowerName: String,
    val division: String,
    val contactNumber: String = "0812 3456 7890",
    val borrowDateTime: String,
    val returnDateTime: String = "06 November 2026 · 18.00 WIB",
    val isReturned: Boolean = false,
    val iconType: String = "HT"
)

object SampleData {
    val initialItems = listOf(
        LogisticsItem(
            id = "1",
            name = "HT Motorola 04",
            code = "HT-004",
            quantity = 1,
            borrowerName = "Dimas Aditya",
            division = "Sie Acara",
            contactNumber = "0812 3456 7890",
            borrowDateTime = "03 Nov 2026 · 08.15 WIB",
            returnDateTime = "06 November 2026 · 18.00 WIB",
            isReturned = false,
            iconType = "HT"
        ),
        LogisticsItem(
            id = "2",
            name = "Kabel Roll 25 Meter",
            code = "KBL-002",
            quantity = 2,
            borrowerName = "Nadia Putri",
            division = "Sie Perlengkapan",
            contactNumber = "0813 9876 5432",
            borrowDateTime = "03 Nov 2026 · 08.30 WIB",
            returnDateTime = "05 November 2026 · 17.00 WIB",
            isReturned = false,
            iconType = "KABEL"
        ),
        LogisticsItem(
            id = "3",
            name = "Pointer Presentasi",
            code = "PTR-001",
            quantity = 1,
            borrowerName = "Hania Putri",
            division = "Sie Acara",
            contactNumber = "0815 1122 3344",
            borrowDateTime = "03 Nov 2026 · 09.00 WIB",
            returnDateTime = "04 November 2026 · 15.00 WIB",
            isReturned = false,
            iconType = "POINTER"
        ),
        // Additional mock items for "Sedang Dipinjam"
        LogisticsItem(
            id = "4",
            name = "Megaphone Toa",
            code = "MGP-001",
            quantity = 1,
            borrowerName = "Rizky Ramadhan",
            division = "Sie Lapangan",
            contactNumber = "0812 2233 4455",
            borrowDateTime = "02 Nov 2026 · 14.00 WIB",
            returnDateTime = "05 November 2026 · 16.00 WIB",
            isReturned = false,
            iconType = "HT"
        ),
        LogisticsItem(
            id = "5",
            name = "Projector Epson",
            code = "PRJ-003",
            quantity = 1,
            borrowerName = "Budi Santoso",
            division = "Sie HPD",
            contactNumber = "0817 6655 4433",
            borrowDateTime = "02 Nov 2026 · 10.00 WIB",
            returnDateTime = "04 November 2026 · 20.00 WIB",
            isReturned = false,
            iconType = "POINTER"
        ),
        LogisticsItem(
            id = "6",
            name = "HT Motorola 02",
            code = "HT-002",
            quantity = 1,
            borrowerName = "Farhan Pratama",
            division = "Sie Keamanan",
            contactNumber = "0819 0011 2233",
            borrowDateTime = "01 Nov 2026 · 08.00 WIB",
            returnDateTime = "06 November 2026 · 18.00 WIB",
            isReturned = false,
            iconType = "HT"
        ),
        LogisticsItem(
            id = "7",
            name = "Terminal Listrik 10 Slot",
            code = "TRM-005",
            quantity = 3,
            borrowerName = "Siti Aisyah",
            division = "Sie Perlengkapan",
            contactNumber = "0812 7788 9900",
            borrowDateTime = "01 Nov 2026 · 11.30 WIB",
            returnDateTime = "05 November 2026 · 17.00 WIB",
            isReturned = false,
            iconType = "KABEL"
        ),
        LogisticsItem(
            id = "8",
            name = "HT Motorola 05",
            code = "HT-005",
            quantity = 1,
            borrowerName = "Andi Wijaya",
            division = "Sie Logistik",
            contactNumber = "0813 4455 6677",
            borrowDateTime = "01 Nov 2026 · 07.45 WIB",
            returnDateTime = "06 November 2026 · 18.00 WIB",
            isReturned = false,
            iconType = "HT"
        ),
        // Returned items (14 items)
        LogisticsItem(
            id = "101",
            name = "HT Motorola 01",
            code = "HT-001",
            quantity = 1,
            borrowerName = "Rina Melati",
            division = "Sie Acara",
            contactNumber = "0812 1111 2222",
            borrowDateTime = "28 Oct 2026 · 08.00 WIB",
            returnDateTime = "30 Oct 2026 · 17.00 WIB",
            isReturned = true,
            iconType = "HT"
        ),
        LogisticsItem(
            id = "102",
            name = "Kabel Roll 50 Meter",
            code = "KBL-001",
            quantity = 1,
            borrowerName = "Agus Prasetyo",
            division = "Sie Perlengkapan",
            contactNumber = "0813 3333 4444",
            borrowDateTime = "27 Oct 2026 · 09.00 WIB",
            returnDateTime = "29 Oct 2026 · 18.00 WIB",
            isReturned = true,
            iconType = "KABEL"
        )
    )
}
