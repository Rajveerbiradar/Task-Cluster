package com.journeytix.taskcluster.data.repository

import com.journeytix.taskcluster.data.db.ParentDao
import com.journeytix.taskcluster.data.db.SectionDao
import com.journeytix.taskcluster.data.db.TaskDao
import com.journeytix.taskcluster.data.model.Parent
import com.journeytix.taskcluster.data.model.Section
import com.journeytix.taskcluster.data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao,
    private val sectionDao: SectionDao,
    private val parentDao: ParentDao,
) {
    // Tasks
    fun observeActiveTasks(): Flow<List<Task>> = taskDao.observeActive()
    fun observeTrashedTasks(): Flow<List<Task>> = taskDao.observeTrashed()
    suspend fun getTask(id: Long): Task? = taskDao.getById(id)
    suspend fun addTask(task: Task): Long = taskDao.insert(task)
    suspend fun updateTask(task: Task) = taskDao.update(task)
    suspend fun deleteTaskPermanently(task: Task) = taskDao.delete(task)
    suspend fun emptyTrash() = taskDao.deleteAllTrashed()
    suspend fun trashAllCompleted() = taskDao.trashAllCompleted(System.currentTimeMillis())
    suspend fun restoreAllTrashed() = taskDao.restoreAllTrashed()
    suspend fun trashTask(id: Long) = taskDao.trashById(id, System.currentTimeMillis())

    // Sections
    fun observeSections(): Flow<List<Section>> = sectionDao.observeAll()
    fun observeTrashedSections(): Flow<List<Section>> = sectionDao.observeTrashed()
    suspend fun getSection(id: Long): Section? = sectionDao.getById(id)
    suspend fun addSection(section: Section): Long = sectionDao.insert(section)
    suspend fun updateSection(section: Section) = sectionDao.update(section)
    suspend fun deleteSection(section: Section) = sectionDao.delete(section)
    // Soft-delete: move the section (and its tasks, by hiding it) to trash.
    suspend fun deleteSection(id: Long) {
        sectionDao.trashById(id, System.currentTimeMillis())
    }
    suspend fun restoreSection(id: Long) = sectionDao.restoreById(id)
    suspend fun deleteSectionPermanently(id: Long) {
        taskDao.deleteBySectionId(id)
        sectionDao.deleteById(id)
    }
    suspend fun sectionCount(): Int = sectionDao.count()

    // Parents
    fun observeParents(): Flow<List<Parent>> = parentDao.observeAll()
    fun observeTrashedParents(): Flow<List<Parent>> = parentDao.observeTrashed()
    suspend fun getParent(id: Long): Parent? = parentDao.getById(id)
    suspend fun addParent(parent: Parent): Long = parentDao.insert(parent)
    suspend fun updateParent(parent: Parent) = parentDao.update(parent)
    suspend fun deleteParent(parent: Parent) = parentDao.delete(parent)
    // Soft-delete: trash the parent and cascade-hide its sections.
    suspend fun deleteParent(id: Long) {
        val ts = System.currentTimeMillis()
        sectionDao.trashByParentId(id, ts)
        parentDao.trashById(id, ts)
    }
    suspend fun restoreParent(id: Long) {
        sectionDao.restoreByParentId(id)
        parentDao.restoreById(id)
    }
    suspend fun deleteParentPermanently(id: Long) {
        val sections = sectionDao.getByParentId(id)
        sections.forEach { taskDao.deleteBySectionId(it.id) }
        sectionDao.deleteByParentId(id)
        parentDao.deleteById(id)
    }
}
