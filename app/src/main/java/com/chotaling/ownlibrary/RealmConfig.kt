package com.chotaling.ownlibrary

import io.realm.Realm
import io.realm.RealmConfiguration

class RealmConfig {

    fun getInstance() : Realm
    {
        val realmName: String = "OwnLibrary"
        val config =
            RealmConfiguration.Builder()
                .name(realmName)
                .allowWritesOnUiThread(true)
                .allowQueriesOnUiThread(true)
                .build();

        return Realm.getInstance(config)
    }
}