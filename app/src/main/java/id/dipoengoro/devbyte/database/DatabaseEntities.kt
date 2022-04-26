package id.dipoengoro.devbyte.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.dipoengoro.devbyte.domain.Video

@Entity
data class DatabaseVideo(
    @PrimaryKey
    val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String
)

fun List<DatabaseVideo>.asDomainModel(): List<Video> =
    map {
        Video(
            url = it.url,
            title = it.title,
            description = it.description,
            updated = it.updated,
            thumbnail = it.thumbnail
        )
    }