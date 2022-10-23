package com.radreichley.noctal.base.db

import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromStringOrNull
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Story() : RealmObject {
    constructor(id: String) : this() {
        this.id = id
    }

    @PrimaryKey
    var id: String = "invalid-id"
    var url: String = ""
    var title: String = ""
    var submitter: String = ""
    var timeAgo: String = ""
    var score: Int = 0
    var numComments: Int = 0
    var storyText: String? = null
    var favIconPath: String? = null
    var imagePath: String? = null

    val displayUrl: String? by lazy {
        val dVal = url

        var host = urlHost ?: return@lazy dVal

        if (host.startsWith("www.")) {
            host = host.substring(4)
        }

        host
    }

    val placeholderLetter: String? by lazy {
        val dVal = null

        val host = urlHost ?: return@lazy dVal
        val parts = host.split(".")
        parts[parts.count() - 2][0].toString().uppercase()
    }

    private val urlHost: String? by lazy {
        val dVal = null

        url ?: return@lazy dVal
        val uri = Uri.fromStringOrNull(url) ?: return@lazy dVal

        uri.host ?: dVal
    }

//    private string ToTimeAgo(DateTimeOffset date)
//    {
//        var delta = DateTimeOffset.UtcNow.Subtract(date);
//
//        if (delta.TotalSeconds < 60)
//        {
//            return "Just now";
//        }
//
//        if (delta.TotalMinutes < 60)
//        {
//            return $"{(int)delta.TotalMinutes}m ago";
//        }
//
//        if (delta.TotalHours < 24)
//        {
//            return $"{(int)delta.TotalHours}h ago";
//        }
//
//        if (delta.TotalDays < 7)
//        {
//            return $"{(int)delta.TotalDays}d ago";
//        }
//
//        return date.ToString("MMMM d, yyyy");
//    }
}


//public record StoriesFeedItem(int Id, string Url, string Title, string Submitter, string TimeAgo, int Score, int NumComments, string? FavIconPath = null, string? ImagePath = null, string? StoryText = null)
//{
//    public string? DisplayUrl { get; } = GetDisplayUrl(Url);
//    public string? PlaceholderLetter { get; } = GetPlaceholderLetter(Url);