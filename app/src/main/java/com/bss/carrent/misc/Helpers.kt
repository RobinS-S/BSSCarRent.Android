package com.bss.carrent.misc

import com.bss.carrent.data.User

object Helpers {
    fun getFormattedName(user: User): String {
        return "${user.firstName} ${if (user.infix != null) "${user.infix} " else ""}${user.lastName}"
    }

    fun formatDoubleWithOptionalDecimals(value: Double): String {
        return if(value != value.toInt().toDouble()) {
            String.format("%.2f", value)
            } else {
            String.format("%.0f", value)
        }
    }
}