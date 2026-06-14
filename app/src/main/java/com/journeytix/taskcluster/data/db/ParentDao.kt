package com.journeytix.taskcluster.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.journeytix.taskcluster.data.model.Parent
import kotlinx.coroutines.flow.Flow

@Dao
interface ParentDao {

    @Insert
    suspend fun insert(parent: Parent): Long

    @Update
    suspend fun update(parent: Parent)

    @Delete
    suspend fun delete(parent: Parent)

    @Query("SELECT * FROM Parent WHERE id = :id")
    suspend fun getById(id: Long): Parent?

    @Query("SELECT * FROM Parent WHERE isTrashed = 0 ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<Parent>>

    @Query("SELECT * FROM Parent WHERE isTrashed = 1 ORDER BY trashedAt DESC")
    fun observeTrashed(): Flow<List<Parent>>

    @Query("UPDATE Parent SET isTrashed = 1, trashedAt = :ts WHERE id = :id")
    suspend fun trashById(id: Long, ts: Long)

    @Query("UPDATE Parent SET isTrashed = 0, trashedAt = NULL WHERE id = :id")
    suspend fun restoreById(id: Long)

    @Query("DELETE FROM Parent WHERE id = :id")
    suspend fun deleteById(id: Long)
}
