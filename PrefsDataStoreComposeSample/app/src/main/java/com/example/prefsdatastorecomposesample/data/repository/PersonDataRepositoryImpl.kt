package com.example.prefsdatastorecomposesample.data.repository

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.prefsdatastorecomposesample.data.SafeResult
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences.PreferencesKeys.HEIGHT
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences.PreferencesKeys.ID
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences.PreferencesKeys.NAME
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences.PreferencesKeys.PHONE_NUMBER
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences.PreferencesKeys.PHONE_TYPE
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences.PreferencesKeys.STUDENT
import com.example.prefsdatastorecomposesample.data.datastore.toPerson
import com.example.prefsdatastorecomposesample.data.safeCall
import com.example.prefsdatastorecomposesample.domain.model.Person
import com.example.prefsdatastorecomposesample.domain.repository.PersonDataRepository
import com.example.prefsdatastorecomposesample.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

internal class PersonDataRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val personDataStore: PersonPreferences,
) : PersonDataRepository {
    override suspend fun savePersonData(
        id: String,
        name: String,
        height: String,
        student: Boolean,
        phoneNumber: String,
        phoneTypeOrdinal: Int
    ): SafeResult<String> {
        return safeCall(dispatcher = dispatcher) {
            personDataStore.dataStore.edit { preferences ->
                preferences[ID] = id.toInt()
                preferences[NAME] = name
                preferences[HEIGHT] = height.toDouble()
                preferences[STUDENT] = student
                preferences[PHONE_NUMBER] = phoneNumber
                preferences[PHONE_TYPE] = phoneTypeOrdinal
            }
            name
        }
    }

    override fun observePersonData(): Flow<Person> {
        return personDataStore.dataStore.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e("PersonDataRepositoryImpl", "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences.toPerson()
        }.flowOn(dispatcher)
    }
}