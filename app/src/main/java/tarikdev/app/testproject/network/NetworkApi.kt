package tarikdev.app.testproject.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tarikdev.app.testproject.model.Comment

interface NetworkApi {

    @GET("comments")
    fun getAllComments(): Call<List<Comment>>

    @GET("comments")
    fun getComments(
        @Query("_start") fromId: Int,
        @Query("_end") toId: Int
    ): Call<List<Comment>>

    // limit=10 - items are returned by default

}