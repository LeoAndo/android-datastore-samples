package com.example.protodatastorecomposesample.domain

import com.example.protodatastorecomposesample.data.SafeResult
import com.example.protodatastorecomposesample.domain.model.Person
import kotlinx.coroutines.flow.Flow

internal interface PersonDataRepository {
    suspend fun savePersonData(person: Person): SafeResult<Person>
    fun observePersonData(): Flow<Person>
}