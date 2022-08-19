package com.senyor_o.usercrudapp.presentation.edit

sealed class EditEvent {
    data class EnteredName(val value: String): EditEvent()

    data class EnteredBirthdate(val value: String): EditEvent()

    object CreateUser: EditEvent()

    object UpdateUser: EditEvent()

    object DeleteUser: EditEvent()

}