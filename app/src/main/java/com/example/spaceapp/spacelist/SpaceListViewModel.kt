package com.example.spaceapp.spacelist

import androidx.lifecycle.ViewModel
import com.example.spaceapp.data.repository.SpaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpaceListViewModel @Inject constructor(
    repository: SpaceRepository) : ViewModel(){
        val repository = repository.getSpace()
    }
