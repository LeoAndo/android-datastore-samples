package com.example.protodatastorecomposesample.ui.home

import com.example.protodatastorecomposesample.domain.model.Person

internal sealed interface HomeUiState {
    object Initial : HomeUiState
    data class Data(val person: Person) : HomeUiState
    object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
}