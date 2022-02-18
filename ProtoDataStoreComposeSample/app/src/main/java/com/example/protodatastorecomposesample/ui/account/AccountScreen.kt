package com.example.protodatastorecomposesample.ui.account

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.protodatastorecomposesample.PersonPreferences
import com.example.protodatastorecomposesample.domain.model.Person
import com.example.protodatastorecomposesample.ui.theme.ProtoDataStoreComposeSampleTheme

@Composable
internal fun AccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel(),
) {
    AccountContent(
        uiState = viewModel.uiState,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountContent(
    uiState: AccountUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            when (uiState) {
                is AccountUiState.Error -> {
                    Text(
                        text = uiState.message, modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    )
                }
                is AccountUiState.Data -> {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        uiState.person.let {
                            Text(text = "id: " + it.id)
                            Text(text = "name: " + it.name)
                            Text(text = "height: " + it.height)
                            Text(text = "student: " + it.student)
                            Text(text = "PhoneNumber: " + it.phoneNumber)
                            Text(text = "PhoneType: " + it.phoneTypeName)
                        }
                    }
                }
                AccountUiState.Initial -> {
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4,
    showSystemUi = true
)
@Composable
fun AccountContent_Preview_Error() {
    ProtoDataStoreComposeSampleTheme {
        AccountContent(
            uiState = AccountUiState.Error(message = "error!!!!!"),
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4,
    showSystemUi = true
)
@Composable
fun AccountContent_Preview_User() {
    val person =
        Person("1", "Yamada", "180.5", true, "080xxxx", PersonPreferences.PhoneType.HOME.name)
    ProtoDataStoreComposeSampleTheme {
        AccountContent(
            uiState = AccountUiState.Data(person = person),
        )
    }
}