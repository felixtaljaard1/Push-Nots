package com.example.spaceapp.injection

import android.content.Context
import com.example.spaceapp.data.local.AppDatabase
import com.example.spaceapp.data.local.SpaceDAO
import com.example.spaceapp.data.remote.SpaceRemoteDataSource
import com.example.spaceapp.data.remote.SpaceService
import com.example.spaceapp.data.repository.SpaceRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGSON(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spaceflightnewsapi.net/v3/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideSpaceService(retrofit: Retrofit) : SpaceService
    = retrofit.create(SpaceService::class.java)

    @Singleton
    @Provides
    fun  provideSpaceRemoteDataSource(spaceService: SpaceService)
    = SpaceRemoteDataSource(spaceService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context)
    = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideSpaceDAO(appDatabase: AppDatabase)
    =appDatabase.spaceDAO()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: SpaceRemoteDataSource, localDataSource: SpaceDAO)
    = SpaceRepository(remoteDataSource, localDataSource)
}