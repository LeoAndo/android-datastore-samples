package com.example.protodatastorecomposesample.ui.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.protodatastorecomposesample.domain.repository.PersonDataRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
internal class AccountViewModel @Inject constructor(
    private val repository: PersonDataRepository,
) : ViewModel() {
    var uiState by mutableStateOf<AccountUiState>(AccountUiState.Initial)
        private set

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            uiState = AccountUiState.Error(throwable.localizedMessage ?: "error!")
        }) {
            repository.observePersonData().collect {
                uiState = AccountUiState.Data(person = it)
            }
        }
    }
}