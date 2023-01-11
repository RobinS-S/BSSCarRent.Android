package com.bss.carrent.api.client

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.LocalDateTime

class LocalDateTimeJsonAdapter : JsonAdapter<LocalDateTime>() {
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        writer.value(value.toString())
    }

    override fun fromJson(reader: JsonReader): LocalDateTime? {
        return LocalDateTime.parse(reader.nextString())
    }
}