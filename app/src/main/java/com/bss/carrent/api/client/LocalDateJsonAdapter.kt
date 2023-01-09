package com.bss.carrent.api.client

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.LocalDate

class LocalDateJsonAdapter : JsonAdapter<LocalDate>() {
    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        writer.value(value.toString())
    }

    override fun fromJson(reader: JsonReader): LocalDate? {
        return LocalDate.parse(reader.nextString())
    }
}