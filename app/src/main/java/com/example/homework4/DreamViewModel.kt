package com.example.homework4

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DreamViewModel(private val repository: DreamRepository) : ViewModel() {
    val allDreams: LiveData<List<Dream>> = repository.allDreams.asLiveData()

    fun insert(dream: Dream) = viewModelScope.launch {
        repository.insertDream(dream)
    }

    fun updateDream(title: String, content: String, reflection: String, emotion: String, id: Int) = viewModelScope.launch {
        repository.updateDream(title, content, reflection, emotion, id)
    }

    fun deleteDream(id: Int) = viewModelScope.launch {
        repository.deleteDream(id)
    }

    fun getDream(id: Int) = repository.getDream(id).asLiveData()
}

class DreamViewModelFactory(private val repository: DreamRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DreamViewModel::class.java)){
            return DreamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}