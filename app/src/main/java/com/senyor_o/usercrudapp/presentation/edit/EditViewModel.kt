package com.senyor_o.usercrudapp.presentation.edit

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senyor_o.usercrudapp.R
import com.senyor_o.usercrudapp.domain.model.Response
import com.senyor_o.usercrudapp.domain.model.User
import com.senyor_o.usercrudapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val userRepo: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableState<EditState> = mutableStateOf(EditState())
    val state: State<EditState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("userId")?.let { userId ->
            if(userId != -1) {
                getUser(userId)
            }
        }
    }

    private fun getUser(userId: Int) = viewModelScope.launch {
        userRepo.getUserById(userId).collect { response ->
            when(response) {
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
                is Response.Success -> response.data?.let { user ->
                    _state.value = _state.value.copy(
                        userId = user.id,
                        name = user.name,
                        birthdate = user.birthdate,
                        displayProgressBar = false
                    )
                }
            }
        }
    }

    private fun postUser() = viewModelScope.launch {
        userRepo.postUser(
            User(
                name = _state.value.name,
                birthdate = _state.value.birthdate
            )
        ).collect { response ->
            when(response) {
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
                is Response.Success -> response.data?.let { _ ->
                    _eventFlow.emit(UIEvent.ActionPerformed(R.string.user_created))
                }
            }
        }
    }

    private fun updateUser() = viewModelScope.launch {
        userRepo.updateUser(
            User(
                id = _state.value.userId!!,
                name = _state.value.name,
                birthdate = _state.value.birthdate
            )
        ).collect { response ->
            when(response) {
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
                is Response.Success -> response.data?.let { _ ->
                    _eventFlow.emit(UIEvent.ActionPerformed(R.string.user_updated))
                }
            }
        }
    }

    private fun deleteUser() = viewModelScope.launch {
        userRepo.deleteUser(_state.value.userId!!).collect { response ->
            when(response) {
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
                is Response.Success -> response.data?.let { _ ->
                    _eventFlow.emit(UIEvent.ActionPerformed(R.string.user_deleted))
                }
            }
        }
    }

    fun hideErrorDialog() {
        _state.value = _state.value.copy(
            errorMessage = null,
        )
    }

    fun onEvent(event: EditEvent) {
        when(event) {
            is EditEvent.EnteredBirthdate -> _state.value = _state.value.copy(
                birthdate = event.value,
            )
            is EditEvent.EnteredName -> _state.value = _state.value.copy(
                name = event.value,
            )
            EditEvent.CreateUser -> {
                postUser()
            }
            EditEvent.DeleteUser -> {
                deleteUser()
            }
            EditEvent.UpdateUser -> {
                updateUser()
            }
        }
    }

    sealed class UIEvent {
        data class ActionPerformed(@StringRes val message: Int): UIEvent()
    }
}
