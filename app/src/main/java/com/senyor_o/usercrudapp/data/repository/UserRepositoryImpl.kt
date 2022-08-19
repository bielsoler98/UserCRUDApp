package com.senyor_o.usercrudapp.data.repository

import com.senyor_o.usercrudapp.domain.api.UserApi
import com.senyor_o.usercrudapp.domain.model.Response.*
import com.senyor_o.usercrudapp.domain.model.User
import com.senyor_o.usercrudapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {
    override suspend fun getUsers() = flow {
        try {
            emit(Loading)
            val users =  userApi.getUsers()
            emit(Success(users))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun getUserById(userId: Int)= flow {
        try {
            emit(Loading)
            val user = userApi.getUserById(userId)
            emit(Success(user))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override fun postUser(user: User) = flow {
        try {
            emit(Loading)
            userApi.postUser(user).awaitResponse()
            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override fun deleteUser(userId: Int) = flow {
        try {
            emit(Loading)
            userApi.deleteUser(userId).awaitResponse()
            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override fun updateUser(user: User) = flow {
        try {
            emit(Loading)
            userApi.updateUser(user).awaitResponse()
            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

}