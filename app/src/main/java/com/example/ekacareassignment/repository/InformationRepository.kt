package com.example.ekacareassignment.repository

import androidx.lifecycle.LiveData
import com.example.ekacareassignment.database.InformationDao
import com.example.ekacareassignment.model.Information

class InformationRepository(private val informationDao: InformationDao) {

    suspend fun insertInformation(information: Information) {
        informationDao.insertInformation(information)
    }

    fun getAllInformation(): LiveData<List<Information>> {
        return informationDao.getAllInformation()
    }

}