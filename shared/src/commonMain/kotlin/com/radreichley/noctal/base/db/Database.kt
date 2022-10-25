package com.radreichley.noctal.base.db

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class Database {
    val realm: Realm

    init {
        val config = RealmConfiguration.Builder(schema = setOf(StoryDao::class, StoryMetaDao::class))
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.open(config)

        realm.writeBlocking {
            deleteAll()
        }
    }

    fun getStories() {

    }
}
