package com.bss.carrent.misc

import com.bss.carrent.R
import com.bss.carrent.data.car.CarType
import com.bss.carrent.data.car.CombustionFuelType
import com.bss.carrent.data.user.UserDto
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Helpers {
    fun getFormattedName(userDto: UserDto): String {
        return "${userDto.firstName} ${if (userDto.infix != null) "${userDto.infix} " else ""}${userDto.lastName}"
    }

    fun formatDoubleWithOptionalDecimals(value: Double): String {
        return if (value != value.toInt().toDouble()) {
            String.format("%.2f", value)
        } else {
            String.format("%.0f", value)
        }
    }

    fun formatShortDate(value: LocalDate): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return value.format(formatter)
    }

    fun parseShortDate(value: String): LocalDate? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return try {
            LocalDate.parse(value, formatter)
        } catch (e: DateTimeParseException) {
            null
        }
        return null
    }

    fun formatDateTime(value: LocalDateTime): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        return value.format(formatter)
    }

    fun formatCurrency(value: Double?): String {
        if (value == null) return "";
        val decimalFormat = DecimalFormat("#,###.##")
        return decimalFormat.format(value)
    }

    fun getCarTypeName(value: CarType): Int {
        return when (value) {
            CarType.COMBUSTION -> R.string.car_type_combustion
            CarType.FUEL_CELL -> R.string.car_type_fuel_cell
            CarType.BATTERY_ELECTRIC -> R.string.car_type_electric
        }
    }

    fun getCombustionFuelTypeName(value: CombustionFuelType): Int {
        return when (value) {
            CombustionFuelType.GASOLINE -> R.string.fuel_type_gasoline
            CombustionFuelType.DIESEL -> R.string.fuel_type_diesel
            CombustionFuelType.GAS -> R.string.car_type_gas
        }
    }

    fun getFormattedPhone(phoneInternationalCode: String, phoneNumber: String): String {
        return "+${phoneInternationalCode}-${phoneNumber}"
    }
}