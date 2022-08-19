package com.senyor_o.usercrudapp.presentation.main

import androidx.annotation.StringRes
import com.senyor_o.usercrudapp.domain.model.User

data class MainScreenState(
    val users: List<User> = emptyList(),
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)