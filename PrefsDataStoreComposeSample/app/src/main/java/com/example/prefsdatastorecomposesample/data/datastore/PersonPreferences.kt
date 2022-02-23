package com.example.prefsdatastorecomposesample.data.datastore

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.*
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences.PreferencesKeys.ID
import com.example.prefsdatastorecomposesample.domain.model.Person
import kotlinx.coroutines.CoroutineScope
import java.io.File
import javax.inject.Inject

internal class PersonPreferences constructor(
    scope: CoroutineScope, produceFile: File
) {
    val dataStore = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
        scope = scope,
        produceFile = { produceFile }
    )

    object PreferencesKeys {
        val ID = intPreferencesKey("id")
        val NAME = stringPreferencesKey("name")
        val HEIGHT = doublePreferencesKey("height")
        val STUDENT = booleanPreferencesKey("student")
        val PHONE_NUMBER = stringPreferencesKey("phone_number")
        val PHONE_TYPE = intPreferencesKey("phone_type")
    }

    enum class PhoneType {
        UNSPECIFIED, MOBILE, HOME, WORK, UNRECOGNIZED
    }
}

internal fun Preferences.toPerson(): Person {
    val id = this[ID] ?: 0
    val name = this[PersonPreferences.PreferencesKeys.NAME] ?: ""
    val height = this[PersonPreferences.PreferencesKeys.HEIGHT] ?: 0
    val student = this[PersonPreferences.PreferencesKeys.STUDENT] ?: false
    val phoneNumber = this[PersonPreferences.PreferencesKeys.PHONE_NUMBER] ?: ""
    val phoneTypeName = PersonPreferences.PhoneType.values()
        .first {
            it.ordinal == (this[PersonPreferences.PreferencesKeys.PHONE_TYPE]
                ?: PersonPreferences.PhoneType.UNSPECIFIED.ordinal)
        }.name
    return Person(
        id = id.toString(),
        name = name,
        height = height.toString(),
        student = student,
        phoneNumber = phoneNumber,
        phoneTypeName = phoneTypeName
    )
}
