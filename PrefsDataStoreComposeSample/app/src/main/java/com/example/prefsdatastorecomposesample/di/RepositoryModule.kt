package com.example.prefsdatastorecomposesample.di

import com.example.prefsdatastorecomposesample.data.repository.PersonDataRepositoryImpl
import com.example.prefsdatastorecomposesample.domain.repository.PersonDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindPersonDataRepository(impl: PersonDataRepositoryImpl): PersonDataRepository
}