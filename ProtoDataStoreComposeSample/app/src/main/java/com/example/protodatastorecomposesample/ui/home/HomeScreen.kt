package com.example.protodatastorecomposesample.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.protodatastorecomposesample.PersonPreferences
import com.example.protodatastorecomposesample.domain.model.Person
import com.example.protodatastorecomposesample.ui.components.FullScreenLoading
import com.example.protodatastorecomposesample.ui.theme.ProtoDataStoreComposeSampleTheme

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeContent(
        uiState = viewModel.uiState,
        modifier = modifier,
        onClickSaveButton = viewModel::savePersonData
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeContent(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    onClickSaveButton: (Person) -> Unit,
) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var isStudent by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var phoneTypeName by remember { mutableStateOf("") }
    var phoneTypeOrdinal by remember { mutableStateOf(PersonPreferences.PhoneType.UNRECOGNIZED.ordinal) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "id") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "name") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "height") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = isStudent, onCheckedChange = { isStudent = it })
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "is Student?")
            }

            Text(text = "Phone Number", color = Color.Green)
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "phoneNumber") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Box() {
                OutlinedTextField(
                    value = phoneTypeName,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = androidx.compose.material.LocalContentColor.current.copy(
                            LocalContentAlpha.current
                        )
                    ),
                    onValueChange = { },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true },
                    placeholder = { Text(text = "select Phone Type") }
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    val menuItems = listOf(
                        PersonPreferences.PhoneType.MOBILE,
                        PersonPreferences.PhoneType.HOME,
                        PersonPreferences.PhoneType.WORK,
                    )
                    menuItems.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                phoneTypeName = it.name
                                phoneTypeOrdinal = it.ordinal
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Phone,
                                    contentDescription = "phone type: ${it.name}"
                                )
                            })
                    }
                }
            }

            OutlinedButton(onClick = {
                onClickSaveButton(
                    Person(
                        id,
                        name,
                        height,
                        isStudent,
                        phoneNumber,
                        phoneTypeOrdinal
                    )
                )
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Save")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Column {
            when (uiState) {
                is HomeUiState.Error -> {
                    Text(
                        text = uiState.message, modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    )
                }
                HomeUiState.Loading -> {
                    FullScreenLoading()
                }
                is HomeUiState.Data -> {
                    Text(
                        text = uiState.person.name, modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    )
                }
                HomeUiState.Initial -> {
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
fun HomeContent_Preview_Error() {
    ProtoDataStoreComposeSampleTheme {
        HomeContent(
            uiState = HomeUiState.Error(message = "error!!!!!"),
            onClickSaveButton = { },
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
fun HomeContent_Preview_Loading() {
    ProtoDataStoreComposeSampleTheme {
        HomeContent(
            uiState = HomeUiState.Loading,
            onClickSaveButton = { },
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
fun HomeContent_Preview_User() {
    val person = Person("1", "Yamada", "180.5", true, "080xxxx", 0)
    ProtoDataStoreComposeSampleTheme {
        HomeContent(
            uiState = HomeUiState.Data(person = person),
            onClickSaveButton = { },
        )
    }
}