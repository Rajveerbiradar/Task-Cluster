package com.journeytix.taskcluster.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.journeytix.taskcluster.data.db.AppDatabase
import com.journeytix.taskcluster.data.preferences.UserPreferences
import com.journeytix.taskcluster.data.repository.TaskRepository
import com.journeytix.taskcluster.ui.screens.home.HomeViewModel
import com.journeytix.taskcluster.ui.screens.settings.SettingsViewModel
import com.journeytix.taskcluster.ui.screens.trash.TrashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "taskcluster.db")
            .fallbackToDestructiveMigration()
            .addMigrations(AppDatabase.MIGRATION_1_2, AppDatabase.MIGRATION_2_3, AppDatabase.MIGRATION_3_4)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO Section (title, isDaily, createdAt, colorKey) VALUES ('Daily', 1, ${System.currentTimeMillis()}, 'default')")
                }
            })
            .build()
    }
    single { get<AppDatabase>().taskDao() }
    single { get<AppDatabase>().sectionDao() }
    single { get<AppDatabase>().parentDao() }
    single { TaskRepository(get(), get(), get()) }
    single { UserPreferences(androidContext()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { TrashViewModel(get(), get()) }
}
