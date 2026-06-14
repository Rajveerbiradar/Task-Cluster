package com.journeytix.taskcluster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Section(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val parentId: Long? = null,
    val title: String,
    val isDaily: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val colorKey: String = "default",
    val iconKey: String? = null,
    val scheduledDate: String? = null, // ISO date (yyyy-MM-dd), null = today/always visible
    val isTrashed: Boolean = false,
    val trashedAt: Long? = null,
)
