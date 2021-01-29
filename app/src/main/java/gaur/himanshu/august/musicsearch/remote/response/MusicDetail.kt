package gaur.himanshu.august.musicsearch.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MusicDetail(

    @PrimaryKey(autoGenerate = true)
    var key:Int=0,

    val artistId: Int,
    val artistName: String?=null,
    val artistViewUrl: String?=null,
    val artworkUrl100: String?=null,
    val artworkUrl30: String?=null,
    val artworkUrl60: String?=null,
    val artworkUrl600: String?=null,
    val collectionArtistId: Int?=null,
    val collectionArtistName: String?=null,
    val collectionArtistViewUrl: String?=null,
    val collectionCensoredName: String?=null,
    val collectionExplicitness: String?=null,
    val collectionHdPrice: Double?=null,
    val collectionId: Int?=null,
    val collectionName: String?=null,
    val collectionPrice: Double?=null,
    val collectionViewUrl: String?=null,
    val contentAdvisoryRating: String?=null,
    val country: String?=null,
    val currency: String?=null,
    val discCount: Int?=null,
    val discNumber: Int?=null,
    val feedUrl: String?=null,
//    val genreIds: List<String>,
//    val genres: List<String>,
    val isStreamable: Boolean?=null,
    val kind: String?=null,
    val longDescription: String?=null,
    val previewUrl: String?=null,
    val primaryGenreName: String?=null,
    val releaseDate: String?=null,
    val shortDescription: String?=null,
    val trackCensoredName: String?=null,
    val trackCount: Int?=null,
    val trackExplicitness: String?=null,
    val trackHdPrice: Double?=null,
    val trackHdRentalPrice: Double?=null,

    val trackId: Int?,
    val trackName: String?,
    val trackNumber: Int?,
    val trackPrice: Double?,
    val trackRentalPrice: Double?,
    val trackTimeMillis: Int?,
    val trackViewUrl: String?,
    val wrapperType: String?
)