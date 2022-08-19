package com.senyor_o.usercrudapp.presentation.edit

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.senyor_o.usercrudapp.R
import com.senyor_o.usercrudapp.presentation.common_component.EventDialog
import com.senyor_o.usercrudapp.presentation.common_component.ProgressBar
import com.senyor_o.usercrudapp.presentation.edit.components.RoundedButton
import com.senyor_o.usercrudapp.presentation.common_component.UserCard
import com.senyor_o.usercrudapp.utils.getDay
import com.senyor_o.usercrudapp.utils.getFormattedDate
import com.senyor_o.usercrudapp.utils.getMonth
import com.senyor_o.usercrudapp.utils.getYear
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(),
    onNavigateBack: (Int?) -> Unit
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    val year = state.birthdate.getYear() ?: mCalendar.get(Calendar.YEAR)
    val month = state.birthdate.getMonth() ?: mCalendar.get(Calendar.MONTH)
    val day = state.birthdate.getDay() ?: mCalendar.get(Calendar.DAY_OF_MONTH)

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            viewModel.onEvent(EditEvent.EnteredBirthdate("$year-${month+1}-${day}T${Calendar.HOUR}:${Calendar.MINUTE}:${Calendar.SECOND}"))
        }, year, month, day
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is EditViewModel.UIEvent.ActionPerformed -> {
                    onNavigateBack(event.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if(state.userId != null) {
                            stringResource(id = R.string.edit_user)
                        } else {
                            stringResource(id = R.string.create_user)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack(null) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "go back"
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter,

        ){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 56.dp)
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
            ) {
                UserCard(
                    name = state.name,
                    birthdate = state.birthdate,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = state.name,
                    onValueChange = {
                        viewModel.onEvent(EditEvent.EnteredName(it))
                    },
                    singleLine = true,
                    label = { Text(text = stringResource(id = R.string.name)) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = state.birthdate.getFormattedDate(),
                    onValueChange = {},
                    singleLine = true,
                    label = { Text(text = stringResource(id = R.string.birthdate)) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                mDatePickerDialog.show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "open calendar"
                            )
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                if(state.userId != null) {
                    RoundedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = stringResource(id = R.string.save_user),
                        onClick = {
                            viewModel.onEvent(
                                EditEvent.UpdateUser
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    RoundedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = stringResource(id = R.string.delete_user),
                        color = Color.Red,
                        onClick = {
                            viewModel.onEvent(
                                EditEvent.DeleteUser
                            )
                        }
                    )
                } else {
                    RoundedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = stringResource(id = R.string.create_user),
                        onClick = {
                            viewModel.onEvent(
                                EditEvent.CreateUser
                            )
                        }
                    )
                }
            }

        }
        if(state.errorMessage != null){
            EventDialog(
                errorMessage = state.errorMessage,
                onDismiss = viewModel::hideErrorDialog
            )
        }
        if(state.displayProgressBar) {
            ProgressBar()
        }
    }
}