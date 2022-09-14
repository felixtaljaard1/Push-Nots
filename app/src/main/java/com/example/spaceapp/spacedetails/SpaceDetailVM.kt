package com.example.spaceapp.spacedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.spaceapp.data.entities.ResultSpaceItem
import com.example.spaceapp.data.repository.SpaceRepository
import com.example.spaceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SpaceDetailVM @Inject constructor(
    private val repository: SpaceRepository) : ViewModel(){

    /**
     * pass id to backend
     * response from backend
     */

    private val _id = MutableLiveData<Int>()

    private val _space = _id.switchMap { id ->
        repository.getSpaceDetailsData(id)
    }

    val space : LiveData<Resource<ResultSpaceItem>> = _space

    /**
     * fun to be used by fragment to pass id to VM
     */

    fun  startDetailsCall(id: Int){
        _id.value = id
    }
}