package com.senyor_o.usercrudapp.domain.repository

import com.senyor_o.usercrudapp.domain.model.Response
import com.senyor_o.usercrudapp.domain.model.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {

    suspend fun getUsers(): Flow<Response<List<User>>>

    suspend fun getUserById(userId: Int): Flow<Response<User>>

    fun postUser(user: User): Flow<Response<Unit>>

    fun deleteUser(userId: Int): Flow<Response<Unit>>

    fun updateUser(user: User): Flow<Response<Unit>>
}