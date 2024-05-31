package com.namemanager
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class NameRepository(private val nameDao: NameDao) {
    val allNames: Flow<List<Name>> = nameDao.getAlphabetizedNames()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(name: Name) {
        nameDao.insert(name)
    }
}
