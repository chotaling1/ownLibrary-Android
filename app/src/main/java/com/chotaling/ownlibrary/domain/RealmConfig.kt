package com.chotaling.ownlibrary.domain

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration

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