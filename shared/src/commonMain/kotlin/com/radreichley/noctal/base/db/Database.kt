package com.radreichley.noctal.base.db

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class Database {
    val realm: Realm

    init {
        val config = RealmConfiguration.Builder(schema = setOf(Story::class))
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.open(config)

        realm.writeBlocking {
            deleteAll()
        }
    }
}
