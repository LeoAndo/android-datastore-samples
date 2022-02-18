package com.example.protodatastorecomposesample.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.protodatastorecomposesample.PersonPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

internal class PersonDataStore @Inject constructor(@ApplicationContext private val context: Context) :
    DataStore<PersonPreferences> {

    companion object {
        private const val DATA_STORE_PERSON_FILE_NAME = "person_prefs.pb"
    }

    private val Context.dataStore: DataStore<PersonPreferences> by dataStore(
        fileName = DATA_STORE_PERSON_FILE_NAME,
        serializer = PersonPreferencesSerializer,
    )

    override val data: Flow<PersonPreferences>
        get() = context.dataStore.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e("PersonDataRepositoryImpl", "error: ", exception)
                emit(PersonPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    override suspend fun updateData(transform: suspend (t: PersonPreferences) -> PersonPreferences): PersonPreferences {
        return context.dataStore.updateData(transform)
    }
}