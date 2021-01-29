package gaur.himanshu.august.musicsearch.remote.response

data class ITuneResponse(
    val resultCount: Int,
    val results: List<MusicDetail>
)