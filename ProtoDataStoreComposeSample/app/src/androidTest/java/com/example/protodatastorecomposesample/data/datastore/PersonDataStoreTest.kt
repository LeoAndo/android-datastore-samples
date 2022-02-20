package com.example.protodatastorecomposesample.data.datastore

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.protodatastorecomposesample.CoroutinesTestRule
import com.example.protodatastorecomposesample.PersonPreferences
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
    private lateinit var personDataStore: PersonDataStore

    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        personDataStore = PersonDataStore(appContext, coroutinesTestRule.testCoroutineScope, "person_prefs_test.pb")
    }

    @Test
    fun test_update_data() {
        coroutinesTestRule.testCoroutineScope.runBlockingTest {
            personDataStore.updateData { prefs ->
                prefs.toBuilder()
                    .setId(1)
                    .setName("Yamada")
                    .setHeight(180.5)
                    .setIsStudent(true)
                    .setPhones(
                        PersonPreferences.PhoneNumber.newBuilder().setNumber("050")
                            .setTypeValue(1).build()
                    )
                    .build()
            }
            val ret = personDataStore.data.first()
            assert(ret.id == 1)
            assert(ret.name == "Yamada")
            assert(ret.height == 180.5)
            assert(ret.isStudent)
            assert(ret.phones.number == "050")
            assert(ret.phones.type == PersonPreferences.PhoneType.MOBILE)
        }
    }
}