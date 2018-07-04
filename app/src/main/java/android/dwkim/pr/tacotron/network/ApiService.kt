package android.dwkim.pr.tacotron.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Urls.GENERATE)
    fun generate(
        @Query("speaker_id") speakerId: Int,
        @Query("text") text: String
    ): Call<ResponseBody>
}