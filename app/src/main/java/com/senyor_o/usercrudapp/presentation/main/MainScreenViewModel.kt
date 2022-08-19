package com.senyor_o.usercrudapp.presentation.main

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senyor_o.usercrudapp.R
import com.senyor_o.usercrudapp.data.repository.UserRepositoryImpl
import com.senyor_o.usercrudapp.domain.model.Response
import com.senyor_o.usercrudapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val userRepo: UserRepository
) :ViewModel() {

    private val _state: MutableState<MainScreenState> = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state
    init {
        getUsers()
    }

    fun getUsers() {
        _state.value = _state.value.copy(
            users = emptyList(),
        )
        viewModelScope.launch {
            userRepo.getUsers().collect { response ->
                when (response) {
                    is Response.Error -> response.e?.let {
                        _state.value = _state.value.copy(
                            errorMessage = R.string.network_error,
                            displayProgressBar = false
                        )
                    }
                    Response.Loading -> {
                        _state.value = _state.value.copy(
                            displayProgressBar = true
                        )
                    }
                    is Response.Success -> response.data?.let { users ->
                        _state.value = _state.value.copy(
                            users = users,
                            displayProgressBar = false
                        )
                    }
                }
            }
        }
    }

    fun hideErrorDialog() {
        _state.value = _state.value.copy(
            errorMessage = null,
        )
    }
}