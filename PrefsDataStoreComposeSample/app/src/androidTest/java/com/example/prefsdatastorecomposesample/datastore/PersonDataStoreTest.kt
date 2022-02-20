package com.example.prefsdatastorecomposesample.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.prefsdatastorecomposesample.data.datastore.PersonPreferences
import com.example.prefsdatastorecomposesample.CoroutinesTestRule
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class PersonDataStoreTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()
    private lateinit var appContext: Context
    private lateinit var personDataStore: PersonPreferences

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        personDataStore =
            PersonPreferences(
                coroutinesTestRule.testCoroutineScope,
                appContext.preferencesDataStoreFile("person_prefs_test")
            )
    }

    @Test
    fun test_update_data() {
        coroutinesTestRule.testCoroutineScope.runBlockingTest {
            personDataStore.dataStore.edit { preferences ->
                preferences[PersonPreferences.PreferencesKeys.ID] = 1
                preferences[PersonPreferences.PreferencesKeys.NAME] = "Yamada"
                preferences[PersonPreferences.PreferencesKeys.HEIGHT] = 180.5
                preferences[PersonPreferences.PreferencesKeys.STUDENT] = true
                preferences[PersonPreferences.PreferencesKeys.PHONE_NUMBER] = "050"
                preferences[PersonPreferences.PreferencesKeys.PHONE_TYPE] = 1
            }
            val preferences = personDataStore.dataStore.data.first()
            assert(preferences[PersonPreferences.PreferencesKeys.ID] == 1)
            assert(preferences[PersonPreferences.PreferencesKeys.NAME] == "Yamada")
            assert(preferences[PersonPreferences.PreferencesKeys.HEIGHT] == 180.5)
            assert(preferences[PersonPreferences.PreferencesKeys.STUDENT] == true)
            assert(preferences[PersonPreferences.PreferencesKeys.PHONE_NUMBER] == "050")
            assert(preferences[PersonPreferences.PreferencesKeys.PHONE_TYPE] == 1)
        }
    }
}