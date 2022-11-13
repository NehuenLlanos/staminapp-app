package com.staminapp.data.network.api

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.Date

class ApiDateTypeAdapter : TypeAdapter<Date?>() {

    override fun write(out: JsonWriter, value: Date?) {
        if (value == null) out.nullValue() else out.value(value.time)
    }

    // in es una palabra reservada y tengo que transformar el numero en un Date
    override fun read(`in`: JsonReader): Date? {
        return Date(`in`.nextLong())
    }
}