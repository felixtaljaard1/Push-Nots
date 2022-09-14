package com.example.spaceapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spaceapp.data.entities.ResultSpace
import com.example.spaceapp.data.entities.ResultSpaceItem


@Dao
interface SpaceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(result: List<ResultSpaceItem>)

    @Query("SELECT * FROM Space_Items")
    fun getAllSpace() : LiveData<List<ResultSpaceItem>>

// this is a test
    /**
     * query for details
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(space: ResultSpaceItem)

    @Query("SELECT * FROM Space_Items WHERE id= :id")
    fun getSpace(id: Int) : LiveData<ResultSpaceItem>
}