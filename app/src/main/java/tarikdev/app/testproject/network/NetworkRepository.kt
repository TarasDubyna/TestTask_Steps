package tarikdev.app.testproject.network

class NetworkRepository(private val api: NetworkApi) {

    fun getAllComments() = api.getAllComments()

    fun getComments(fromId: Int, toId: Int) = api.getComments(fromId, toId)

}