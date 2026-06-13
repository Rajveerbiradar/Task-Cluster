package com.journeytix.taskcluster.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.journeytix.taskcluster.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM Task WHERE id = :id")
    suspend fun getById(id: Long): Task?

    @Query("SELECT * FROM Task WHERE isTrashed = 0 ORDER BY createdAt DESC")
    fun observeActive(): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE isTrashed = 1 ORDER BY trashedAt DESC")
    fun observeTrashed(): Flow<List<Task>>

    @Query("DELETE FROM Task WHERE isTrashed = 1")
    suspend fun deleteAllTrashed()

    @Query("UPDATE Task SET isTrashed = 1, trashedAt = :now WHERE isCompleted = 1 AND isTrashed = 0")
    suspend fun trashAllCompleted(now: Long)

    @Query("UPDATE Task SET isTrashed = 0, trashedAt = NULL WHERE isTrashed = 1")
    suspend fun restoreAllTrashed()

    @Query("DELETE FROM Task WHERE sectionId = :sectionId")
    suspend fun deleteBySectionId(sectionId: Long)

    @Query("UPDATE Task SET isTrashed = 1, trashedAt = :now WHERE id = :id")
    suspend fun trashById(id: Long, now: Long)
}
