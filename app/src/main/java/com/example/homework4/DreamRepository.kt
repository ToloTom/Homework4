package com.example.homework4

import kotlinx.coroutines.flow.Flow

class DreamRepository(private val dreamDao: DreamDAO) {
    val allDreams: Flow<List<Dream>> = dreamDao.getAllDreams()

    suspend fun insertDream(dream: Dream){
        dreamDao.insertDream(dream)
    }

    suspend fun updateDream(title: String, content: String, reflection: String, emotion: String, id: Int){
        dreamDao.updateDream(title, content, reflection, emotion, id)
    }

    suspend fun deleteDream(id: Int){
        dreamDao.deleteDream(id)
    }

    fun getDream(id: Int): Flow<Dream>{
        return dreamDao.getDream(id)
    }
}