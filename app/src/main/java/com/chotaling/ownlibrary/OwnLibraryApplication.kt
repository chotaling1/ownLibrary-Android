package com.chotaling.ownlibrary

import android.app.Application
import android.content.res.Configuration
import io.realm.Realm
import io.realm.RealmConfiguration

class OwnLibraryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this);
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

}