package com.example.prefsdatastorecomposesample.domain.repository

import com.example.prefsdatastorecomposesample.data.SafeResult
import com.example.prefsdatastorecomposesample.domain.model.Person
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