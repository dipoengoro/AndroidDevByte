package id.dipoengoro.devbyte.network

import com.squareup.moshi.JsonClass
import id.dipoengoro.devbyte.database.DatabaseVideo
import id.dipoengoro.devbyte.domain.Video

@JsonClass(generateAdapter = true)
data class NetworkVideoContainer(val videos: List<NetworkVideo>)

@JsonClass(generateAdapter = true)
data class NetworkVideo(
    val title: String,
    val description: String,
    val url: String,
    val updated: String,
    val thumbnail: String,
    val closedCaptions: String?
)

fun NetworkVideoContainer.asDomainModel(): List<Video> =
    videos.map {
        Video(
            title = it.title,
            description = it.description,
            url = it.url,
            updated = it.updated,
            thumbnail = it.thumbnail
        )
    }

fun NetworkVideoContainer.asDatabaseModel(): Array<DatabaseVideo> =
    videos.map {
        DatabaseVideo(
            title = it.title,
            description = it.description,
            url = it.url,
            updated = it.updated,
            thumbnail = it.thumbnail
        )
    }.toTypedArray()