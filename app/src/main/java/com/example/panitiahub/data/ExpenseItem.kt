package com.example.panitiahub.data

import java.util.UUID

data class ExpenseItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val date: String,
    val category: String, // "Perlengkapan", "Konsumsi", "PDD", "Transportasi"
    val amount: Long,
    val hasReceipt: Boolean = true,
    val receiptFileName: String? = "nota_baterai_03nov.jpg",
    val dateTimeFull: String = "03 November 2026 · 10.30 WIB",
    val picName: String = "Dimas Aditya",
    val note: String = "Pengeluaran operasional panitia acara."
)

object SampleExpenseData {
    val totalExpense: Long = 3850000L
    val remainingBudget: Long = 1150000L
    val recordedReceiptsCount: Int = 18

    val initialExpenses = listOf(
        ExpenseItem(
            id = "1",
            title = "Beli Baterai Mic & HT",
            date = "03 Nov",
            category = "Perlengkapan",
            amount = 75000L,
            hasReceipt = true,
            receiptFileName = "nota_baterai_03nov.jpg",
            dateTimeFull = "03 November 2026 · 10.30 WIB",
            picName = "Dimas Aditya"
        ),
        ExpenseItem(
            id = "2",
            title = "Snack Briefing Panitia",
            date = "02 Nov",
            category = "Konsumsi",
            amount = 250000L,
            hasReceipt = true,
            receiptFileName = "nota_snack_02nov.jpg",
            dateTimeFull = "02 November 2026 · 09.15 WIB",
            picName = "Nadia Putri"
        ),
        ExpenseItem(
            id = "3",
            title = "Cetak Banner Panggung",
            date = "02 Nov",
            category = "PDD",
            amount = 420000L,
            hasReceipt = true,
            receiptFileName = "nota_banner_02nov.jpg",
            dateTimeFull = "02 November 2026 · 14.00 WIB",
            picName = "Hania Putri"
        ),
        ExpenseItem(
            id = "4",
            title = "Bensin Operasional",
            date = "01 Nov",
            category = "Transportasi",
            amount = 100000L,
            hasReceipt = true,
            receiptFileName = "nota_bensin_01nov.jpg",
            dateTimeFull = "01 November 2026 · 08.00 WIB",
            picName = "Rizky Ramadhan"
        ),
        ExpenseItem(
            id = "5",
            title = "Sewa Sound System",
            date = "31 Oct",
            category = "Perlengkapan",
            amount = 1500000L,
            hasReceipt = true,
            receiptFileName = "nota_sound.jpg",
            dateTimeFull = "31 Oktober 2026 · 16.30 WIB",
            picName = "Dimas Aditya"
        )
    )
}
