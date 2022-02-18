package com.example.protodatastorecomposesample.data.repository

import com.example.protodatastorecomposesample.PersonPreferences
import com.example.protodatastorecomposesample.data.SafeResult
import com.example.protodatastorecomposesample.data.datastore.PersonDataStore
import com.example.protodatastorecomposesample.data.safeCall
import com.example.protodatastorecomposesample.di.IoDispatcher
import com.example.protodatastorecomposesample.domain.repository.PersonDataRepository
import com.example.protodatastorecomposesample.domain.model.Person
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class PersonDataRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val dataStore: PersonDataStore,
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
            dataStore.updateData { prefs ->
                prefs.toBuilder()
                    .setId(id.toInt())
                    .setName(name)
                    .setHeight(height.toDouble())
                    .setIsStudent(student)
                    .setPhones(
                        PersonPreferences.PhoneNumber.newBuilder().setNumber(phoneNumber)
                            .setTypeValue(phoneTypeOrdinal).build()
                    )
                    .build()
            }
            name
        }
    }

    override fun observePersonData(): Flow<Person> {
        return dataStore.data.map {
            Person(
                id = it.id.toString(),
                name = it.name,
                height = it.height.toString(),
                student = it.isStudent,
                phoneNumber = it.phones.number,
                phoneTypeName = it.phones.type.name
            )
        }.flowOn(dispatcher)
    }
}