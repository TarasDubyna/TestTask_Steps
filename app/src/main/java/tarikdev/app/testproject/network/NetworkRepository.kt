package tarikdev.app.testproject.network

class NetworkRepository(private val api: NetworkApi) {

    fun getAllComments() = api.getAllComments()

}