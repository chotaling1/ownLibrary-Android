package com.chotaling.ownlibrary

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import io.realm.Realm
import io.realm.RealmConfiguration

class OwnLibraryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        setContext(this)
        Realm.init(this);
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }



    companion object {

        private lateinit var context : Context

        fun setContext(con : Context)
        {
            context = con
        }

        fun getApplicationContext() : Context
        {
            return context
        }
    }
}