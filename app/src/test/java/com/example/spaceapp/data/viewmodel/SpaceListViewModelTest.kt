package com.example.spaceapp.data.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.PrimaryKey
import com.example.spaceapp.data.entities.ResultSpaceItem
import com.example.spaceapp.data.repository.SpaceRepository
import com.example.spaceapp.spacelist.SpaceListViewModel
import com.example.spaceapp.utils.Resource
import io.mockk.MockKSettings.relaxed
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SpaceListViewModelTest {
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SpaceListViewModel
    private val spaceList = listOf(
        ResultSpaceItem(true, 1, "", "", "10/10/10", "", "", "10/9/09", ""),
        ResultSpaceItem(true, 2, "", "", "20/10/10", "", "", "20/9/09", "")

    )

    private val repository: SpaceRepository = mockk(relaxed = true){
        every { getSpace() } returns MutableLiveData(Resource.success(spaceList))
    }

    private val spacesObserver: Observer<Resource<List<ResultSpaceItem>>> = mockk(relaxed = true)

    @Before
    fun setUp(){
        viewModel = SpaceListViewModel(repository)
        viewModel.repository.observeForever(spacesObserver)
    }

    @Test
    fun `get space should return emit list of spaces`(){
        verify { spacesObserver.onChanged(Resource.success(spaceList)) }
        assert(viewModel.repository.value == Resource.success(spaceList))
    }
}
