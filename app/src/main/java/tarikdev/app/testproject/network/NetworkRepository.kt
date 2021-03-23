package tarikdev.app.testproject.network

class NetworkRepository(private val api: NetworkApi) {

    suspend fun getAllComments() = api.getAllComments()

}