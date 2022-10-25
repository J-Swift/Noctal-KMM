package com.radreichley.noctal.base.db

import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromStringOrNull
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant

class StoryDao() : RealmObject {
    constructor(id: String) : this() {
        this.id = id
    }

    @PrimaryKey
    var id: String = "invalid-id"
    var createdAt: Instant = Instant.DISTANT_PAST
    var url: String = ""
    var title: String = ""
    var submitter: String = ""
    var score: Int = 0
    var numComments: Int = 0
    var storyText: String? = null
}

class StoryMetaDao() : RealmObject {
    constructor(id: String) : this() {
        this.id = id
    }

    @PrimaryKey
    var id: String = "invalid-id"
    var favIconPath: String? = null
    var imagePath: String? = null
}
