package com.example.ekacareassignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ekacareassignment.model.Information

@Dao
interface InformationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInformation(information: Information)

    @Query("SELECT * FROM information_table")
    fun getAllInformation(): LiveData<List<Information>>
}