package com.example.prefsdatastorecomposesample.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
    @Singleton
    @Provides
    fun providePersonDataStore(
        @ApplicationContext context: Context,
        @IoCoroutineScope scope: CoroutineScope
    ): PersonPreferences =
        PersonPreferences(scope, context.preferencesDataStoreFile("person_prefs"))
}