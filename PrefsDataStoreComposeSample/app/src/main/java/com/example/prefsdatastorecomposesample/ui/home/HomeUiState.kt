package com.example.prefsdatastorecomposesample.ui.home

internal sealed interface HomeUiState {
    object Initial : HomeUiState
    data class Data(val personName: String) : HomeUiState
    object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
}