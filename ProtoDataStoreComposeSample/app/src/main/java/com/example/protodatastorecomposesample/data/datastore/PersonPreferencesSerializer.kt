package com.example.protodatastorecomposesample.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.protodatastorecomposesample.PersonPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object PersonPreferencesSerializer : Serializer<PersonPreferences> {
    override val defaultValue: PersonPreferences = PersonPreferences.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): PersonPreferences {
        try {
            return PersonPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: PersonPreferences, output: OutputStream) = t.writeTo(output)
}
