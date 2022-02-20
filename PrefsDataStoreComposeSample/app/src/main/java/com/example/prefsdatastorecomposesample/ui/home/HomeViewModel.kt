package com.example.prefsdatastorecomposesample.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
// import androidx.compose.ui.tooling.data.EmptyGroup.data
import androidx.lifecycle.viewModelScope
import com.example.prefsdatastorecomposesample.ui.home.HomeUiState
// import com.example.prefsdatastorecomposesample.data.SafeResult
// import com.example.prefsdatastorecomposesample.domain.repository.PersonDataRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    // private val repository: PersonDataRepository,
) : ViewModel() {
    var uiState by mutableStateOf<HomeUiState>(HomeUiState.Initial)
        private set

    init {
        /*
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            uiState = HomeUiState.Error(throwable.localizedMessage ?: "error!")
        }) {
            repository.observePersonData().collect {
                uiState = HomeUiState.Data(personName = it.name)
            }
        }
         */
    }

    fun savePersonData(
        id: String,
        name: String,
        height: String,
        student: Boolean,
        phoneNumber: String,
        phoneTypeOrdinal: Int
    ) {
        uiState = HomeUiState.Loading
        /*
        viewModelScope.launch {
            uiState = when (val ret = repository.savePersonData(
                id,
                name,
                height,
                student,
                phoneNumber,
                phoneTypeOrdinal
            )) {
                is SafeResult.Error -> {
                    HomeUiState.Error(message = ret.errorResult.localizedMessage ?: "error.")
                }
                is SafeResult.Success -> {
                    HomeUiState.Data(personName = ret.data)
                }
            }
        }
         */
    }
}