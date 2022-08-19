package com.senyor_o.usercrudapp.domain.api


import retrofit2.Call
import com.senyor_o.usercrudapp.domain.model.User
import com.senyor_o.usercrudapp.utils.Constants
import retrofit2.http.*

interface UserApi {

    @GET(Constants.USER_ENDPOINT)
    suspend fun getUsers(): List<User>

    @GET("${Constants.USER_ENDPOINT}/{id}")
    suspend fun getUserById( @Path("id") userId: Int): User

    @POST(Constants.USER_ENDPOINT)
    fun postUser(@Body user: User): Call<Unit>

    @PUT(Constants.USER_ENDPOINT)
    fun updateUser(@Body user: User): Call<Unit>

    @DELETE("${Constants.USER_ENDPOINT}/{id}")
    fun deleteUser( @Path("id") userId: Int): Call<Unit>

}