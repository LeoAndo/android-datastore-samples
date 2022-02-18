package com.example.protodatastorecomposesample.di

import com.example.protodatastorecomposesample.data.repository.PersonDataRepositoryImpl
import com.example.protodatastorecomposesample.domain.PersonDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindUnsplashRepository(impl: PersonDataRepositoryImpl): PersonDataRepository
}