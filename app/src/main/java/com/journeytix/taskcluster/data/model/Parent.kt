package com.journeytix.taskcluster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Parent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val emoji: String? = null,
    val colorKey: String = "default",
    val isFavourite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val scheduledDate: String? = null, // ISO date (yyyy-MM-dd), null = today/always visible
)
