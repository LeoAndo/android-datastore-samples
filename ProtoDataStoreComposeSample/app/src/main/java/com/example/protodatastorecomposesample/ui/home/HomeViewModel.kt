package com.example.protodatastorecomposesample.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf<HomeUiState>(HomeUiState.Initial)
        private set

    init {
        // TODO: data storeの最新の情報を取得する(Flow)
    }

    fun savePersonData(
        id: String,
        name: String,
        height: String,
        student: Boolean,
        phoneNumber: String,
        phoneTypeOrdinal: Int
    ) {
        // TODO: data storeにデータを登録する.
    }
}