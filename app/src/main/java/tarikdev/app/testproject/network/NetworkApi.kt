package tarikdev.app.testproject.network

import retrofit2.http.GET
import tarikdev.app.testproject.model.Comment

interface NetworkApi {

    @GET("comments")
    suspend fun getAllComments(): List<Comment>

}