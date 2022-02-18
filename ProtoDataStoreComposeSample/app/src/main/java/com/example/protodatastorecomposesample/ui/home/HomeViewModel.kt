package com.example.protodatastorecomposesample.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.protodatastorecomposesample.data.SafeResult
import com.example.protodatastorecomposesample.domain.PersonDataRepository
import com.example.protodatastorecomposesample.domain.model.Person
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val repository: PersonDataRepository,
) : ViewModel() {
    var uiState by mutableStateOf<HomeUiState>(HomeUiState.Initial)
        private set

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            uiState = HomeUiState.Error(throwable.localizedMessage ?: "error!")
        }) {
            repository.observePersonData().collect {
                uiState = HomeUiState.Data(person = it)
            }
        }
    }

    fun savePersonData(person: Person) {
        uiState = HomeUiState.Loading
        viewModelScope.launch {
            uiState = when (val ret = repository.savePersonData(person)) {
                is SafeResult.Error -> {
                    HomeUiState.Error(message = ret.errorResult.localizedMessage ?: "error.")
                }
                is SafeResult.Success -> {
                    HomeUiState.Data(person = ret.data)
                }
            }
        }
    }
}