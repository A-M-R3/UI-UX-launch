package com.universitatcarlemany.activity3.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter : TypeAdapter<LocalDate>() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE // Formato estándar: yyyy-MM-dd

    override fun write(out: JsonWriter, value: LocalDate?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(value.format(formatter)) // Lo convierte a texto limpio antes de enviarlo por POST
        }
    }

    override fun read(input: JsonReader): LocalDate {
        return LocalDate.parse(input.nextString(), formatter)
    }
}