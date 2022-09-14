package com.example.spaceapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Space_Items")
data class ResultSpaceItem(
    //val events: List<Any>,
    val featured: Boolean,
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    //val launches: List<Launche>,
    val newsSite: String,
    val publishedAt: String,
    val summary: String,
    val title: String,
    val updatedAt: String,
    val url: String
)