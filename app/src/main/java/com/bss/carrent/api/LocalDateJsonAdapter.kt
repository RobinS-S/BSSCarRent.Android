package com.bss.carrent.api

import com.squareup.moshi.*
import java.time.LocalDate

class LocalDateJsonAdapter : JsonAdapter<LocalDate>() {
    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        writer.value(value.toString())
    }

    override fun fromJson(reader: JsonReader): LocalDate? {
        return LocalDate.parse(reader.nextString())
    }
}