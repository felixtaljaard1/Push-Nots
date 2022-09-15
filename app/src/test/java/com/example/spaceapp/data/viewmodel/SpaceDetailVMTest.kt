package com.example.spaceapp.data.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.spaceapp.data.entities.ResultSpaceItem
import com.example.spaceapp.data.repository.SpaceRepository
import com.example.spaceapp.spacedetails.SpaceDetailVM
import com.example.spaceapp.utils.Resource
import io.mockk.MockKSettings.relaxed
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SpaceDetailVMTest {
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SpaceDetailVM
    private val spaceOne = ResultSpaceItem(true, 2, "", "", "20/10/10", "", "", "20/9/09", "")

    private val repository: SpaceRepository = mockk(relaxed = true){
        every { getSpaceDetailsData(1) } returns MutableLiveData(Resource.success(spaceOne))
    }

    private val spaceObserver: Observer<Resource<ResultSpaceItem>> = mockk(relaxed = true)

    @Before
    fun setUp(){
        viewModel = SpaceDetailVM(repository)
        viewModel.space.observeForever(spaceObserver)
    }

    @Test
    fun ` get space details should return one character`(){
        verify { spaceObserver.onChanged(Resource.success(spaceOne)) }
        assert(viewModel.space.value == Resource.success(spaceOne))
    }
}