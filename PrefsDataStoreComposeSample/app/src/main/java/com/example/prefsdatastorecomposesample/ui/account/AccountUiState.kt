package com.example.prefsdatastorecomposesample.ui.account

import com.example.prefsdatastorecomposesample.domain.model.Person

internal sealed interface AccountUiState {
    object Initial : AccountUiState
    data class Data(val person: Person) : AccountUiState
    data class Error(val message: String) : AccountUiState
}