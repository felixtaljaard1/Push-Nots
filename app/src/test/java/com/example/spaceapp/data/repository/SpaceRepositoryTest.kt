package com.example.spaceapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.spaceapp.data.entities.ResultSpace
import com.example.spaceapp.data.entities.ResultSpaceItem
import com.example.spaceapp.data.local.SpaceDAO
import com.example.spaceapp.data.remote.SpaceRemoteDataSource
import com.example.spaceapp.utils.Resource
import io.mockk.MockKSettings.relaxed
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SpaceRepositoryTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val spacesObserver: Observer<Resource<List<ResultSpaceItem>>> = mockk(relaxed = true)
    private val spaceObserver: Observer<Resource<ResultSpaceItem>> = mockk(relaxed = true)

    private val remoteDataSource: SpaceRemoteDataSource = mockk(relaxed = true)
    private val localDataSource: SpaceDAO = mockk(relaxed = true)
    private lateinit var repository: SpaceRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        repository = SpaceRepository(remoteDataSource, localDataSource)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `get space should return livedata of space API data`(){
        repository.getSpace().observeForever(spacesObserver)

        verify { spacesObserver.onChanged(any()) }
    }

    @Test
    fun `get space item should return livedata of single space API data`(){
        repository.getSpaceDetailsData(1).observeForever(spaceObserver)
        verify { spaceObserver.onChanged(any()) }
    }

}