package com.journeytix.taskcluster.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.journeytix.taskcluster.data.model.Section
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionDao {

    @Insert
    suspend fun insert(section: Section): Long

    @Update
    suspend fun update(section: Section)

    @Delete
    suspend fun delete(section: Section)

    @Query("SELECT * FROM Section WHERE id = :id")
    suspend fun getById(id: Long): Section?

    @Query("SELECT * FROM Section WHERE isTrashed = 0 ORDER BY isDaily DESC, createdAt DESC")
    fun observeAll(): Flow<List<Section>>

    @Query("SELECT * FROM Section WHERE isTrashed = 1 ORDER BY trashedAt DESC")
    fun observeTrashed(): Flow<List<Section>>

    @Query("SELECT COUNT(*) FROM Section")
    suspend fun count(): Int

    @Query("SELECT * FROM Section WHERE parentId = :parentId")
    suspend fun getByParentId(parentId: Long): List<Section>

    @Query("UPDATE Section SET isTrashed = 1, trashedAt = :ts WHERE id = :id")
    suspend fun trashById(id: Long, ts: Long)

    @Query("UPDATE Section SET isTrashed = 1, trashedAt = :ts WHERE parentId = :parentId")
    suspend fun trashByParentId(parentId: Long, ts: Long)

    @Query("UPDATE Section SET isTrashed = 0, trashedAt = NULL WHERE id = :id")
    suspend fun restoreById(id: Long)

    @Query("UPDATE Section SET isTrashed = 0, trashedAt = NULL WHERE parentId = :parentId")
    suspend fun restoreByParentId(parentId: Long)

    @Query("DELETE FROM Section WHERE parentId = :parentId")
    suspend fun deleteByParentId(parentId: Long)

    @Query("DELETE FROM Section WHERE id = :id")
    suspend fun deleteById(id: Long)
}
