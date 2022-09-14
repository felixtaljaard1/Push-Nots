package com.example.spaceapp.data.remote

import com.example.spaceapp.data.entities.ResultSpace
import com.example.spaceapp.data.entities.ResultSpaceItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceService {
    @GET("articles")
    suspend fun getAllSpace() : Response<ResultSpace>

    // appending ID to the url path
    @GET("articles/{id}")
    suspend fun getSpaceDetails(@Path("id")id: Int): Response<ResultSpaceItem>
}