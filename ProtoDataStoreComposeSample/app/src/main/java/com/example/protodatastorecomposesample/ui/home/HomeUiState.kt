package com.example.protodatastorecomposesample.ui.home

internal sealed interface HomeUiState {
    object Initial : HomeUiState
    data class User(val data: String) : HomeUiState
    object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
}