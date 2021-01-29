package gaur.himanshu.august.musicsearch.remote

import gaur.himanshu.august.musicsearch.remote.response.ITuneResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("in/search")
    suspend fun getAllTracks(@Query("term") searchKey: String): Response<ITuneResponse>


}