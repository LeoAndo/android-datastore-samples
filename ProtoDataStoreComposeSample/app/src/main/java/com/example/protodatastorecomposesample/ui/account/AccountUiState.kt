package com.example.protodatastorecomposesample.ui.account

import com.example.protodatastorecomposesample.domain.model.Person

internal sealed interface AccountUiState {
    object Initial : AccountUiState
    data class Data(val person: Person) : AccountUiState
    data class Error(val message: String) : AccountUiState
}