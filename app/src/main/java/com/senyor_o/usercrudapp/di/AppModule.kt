package com.senyor_o.usercrudapp.di

import com.senyor_o.usercrudapp.data.repository.UserRepositoryImpl
import com.senyor_o.usercrudapp.domain.api.UserApi
import com.senyor_o.usercrudapp.domain.repository.UserRepository
import com.senyor_o.usercrudapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi
    ): UserRepository = UserRepositoryImpl(
        userApi = userApi
    )

    @Provides
    @Singleton
    fun provideUserApi(builder: Retrofit.Builder): UserApi = builder
        .build()
        .create(UserApi::class.java)
}