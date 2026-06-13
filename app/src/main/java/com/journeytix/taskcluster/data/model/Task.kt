package com.journeytix.taskcluster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sectionId: Long,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.NONE,
    val dueDate: Long? = null,
    val dueTime: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val isTrashed: Boolean = false,
    val trashedAt: Long? = null,
)

enum class Priority { NONE, LOW, MEDIUM, HIGH }
