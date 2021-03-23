package tarikdev.app.testproject.model

data class NetworkResult<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T): NetworkResult<T> =
            NetworkResult(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): NetworkResult<T> =
            NetworkResult(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): NetworkResult<T> =

            NetworkResult(status = Status.LOADING, data = data, message = null)

    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}