package com.namemanager
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NameDao {
    @Query("SELECT * FROM name_tb ORDER BY name ASC")
    fun getAlphabetizedNames(): Flow<List<Name>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(name: Name)

    @Query("DELETE FROM name_tb")
    suspend fun deleteAll()
}
