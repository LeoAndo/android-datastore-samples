package com.example.protodatastorecomposesample.domain.repository

import com.example.protodatastorecomposesample.data.SafeResult
import com.example.protodatastorecomposesample.domain.model.Person
import kotlinx.coroutines.flow.Flow

internal interface PersonDataRepository {
    suspend fun savePersonData(
        id: String,
        name: String,
        height: String,
        student: Boolean,
        phoneNumber: String,
        phoneTypeOrdinal: Int
    ): SafeResult<String>

    fun observePersonData(): Flow<Person>
}