package com.journeytix.taskcluster.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.journeytix.taskcluster.data.model.Parent
import com.journeytix.taskcluster.data.model.Section
import com.journeytix.taskcluster.data.model.Task

@Database(
    entities = [Task::class, Section::class, Parent::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun sectionDao(): SectionDao
    abstract fun parentDao(): ParentDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Section ADD COLUMN iconKey TEXT")
            }
        }
    }
}
