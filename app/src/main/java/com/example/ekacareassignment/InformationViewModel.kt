package com.example.ekacareassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekacareassignment.model.Information
import com.example.ekacareassignment.repository.InformationRepository
import kotlinx.coroutines.launch

class InformationViewModel(private val repository: InformationRepository) : ViewModel() {

    val allInformation: LiveData<List<Information>> = repository.getAllInformation()

    fun saveInformation(information: Information) {
        viewModelScope.launch {
            repository.insertInformation(information)
        }
    }
}