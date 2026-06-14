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
    version = 3,
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

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Section ADD COLUMN scheduledDate TEXT")
                db.execSQL("ALTER TABLE Parent ADD COLUMN scheduledDate TEXT")
            }
        }
    }
}
