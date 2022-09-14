package com.example.spaceapp.data.remote

import javax.inject.Inject

class SpaceRemoteDataSource @Inject constructor (
    private val spaceService: SpaceService) : BaseDataSource(){
        suspend fun getSpace() = getResult { spaceService.getAllSpace()}
        suspend fun getSpaceById(id: Int) = getResult { spaceService.getSpaceDetails(id) }
    }
