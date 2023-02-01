package com.fininfo.loginexample.database

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmDataBase :Application() {


    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration=RealmConfiguration.Builder()
                        .name("userData.db")
                        .deleteRealmIfMigrationNeeded()
                        .schemaVersion(0)
                        .allowWritesOnUiThread(true)
                        .allowQueriesOnUiThread(true)
                        .build()
        Realm.setDefaultConfiguration(configuration)
    }
}