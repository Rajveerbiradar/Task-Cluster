package com.journeytix.taskcluster

import android.app.Application
import com.journeytix.taskcluster.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TaskClusterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TaskClusterApp)
            modules(appModule)
        }
    }
}
