package com.senyor_o.usercrudapp.presentation.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.senyor_o.usercrudapp.presentation.navigation.AppScreen
import com.senyor_o.usercrudapp.R
import com.senyor_o.usercrudapp.presentation.common_component.EventDialog
import com.senyor_o.usercrudapp.presentation.common_component.ProgressBar
import com.senyor_o.usercrudapp.presentation.common_component.UserCard
import com.senyor_o.usercrudapp.utils.getFormattedDate
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .height(52.dp)
                    .widthIn(min = 52.dp),
                onClick = {
                    navController.navigate(AppScreen.EditScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add fabButton"
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                state.users.forEach{ user ->
                    item {
                        UserCard(
                            name = user.name,
                            birthdate = user.birthdate.getFormattedDate(),
                            onClick = {
                                navController.navigate(route = AppScreen.EditScreen.passId(user.id))
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
}