package com.senyor_o.usercrudapp.presentation.edit

import androidx.annotation.StringRes
import com.senyor_o.usercrudapp.domain.model.User

data class EditState(
    val userId: Int? = null,
    val name: String = "",
    val birthdate: String = "",
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)