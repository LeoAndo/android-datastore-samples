package com.example.protodatastorecomposesample.di

import android.content.Context
import com.example.protodatastorecomposesample.data.datastore.PersonDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
    @Singleton
    @Provides
    fun providePersonDataStore(@ApplicationContext context: Context): PersonDataStore {
        return PersonDataStore(context)
    }
}