package com.bss.carrent.misc

import com.bss.carrent.R
import com.bss.carrent.data.car.CarType
import com.bss.carrent.data.car.CombustionFuelType
import com.bss.carrent.data.user.UserDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    fun getCarTypeName(value: CarType): Int {
        return when(value) {
            CarType.COMBUSTION -> R.string.car_type_combustion
            CarType.FUEL_CELL -> R.string.car_type_fuel_cell
            CarType.BATTERY_ELECTRIC -> R.string.car_type_electric
        }
    }

    fun getCombustionFuelTypeName(value: CombustionFuelType): Int {
        return when(value) {
            CombustionFuelType.GASOLINE -> R.string.fuel_type_gasoline
            CombustionFuelType.DIESEL -> R.string.fuel_type_diesel
            CombustionFuelType.GAS -> R.string.car_type_gas
        }
    }
}