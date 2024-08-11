package com.example.kongsikereta.util

import androidx.compose.ui.text.intl.Locale
import java.text.SimpleDateFormat

object DateConverter {
    fun millisToFormattedDate(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return simpleDateFormat.format(date)
    }
}