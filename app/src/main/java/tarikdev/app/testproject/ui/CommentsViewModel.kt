package tarikdev.app.testproject.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import tarikdev.app.testproject.model.Comment
import tarikdev.app.testproject.model.NetworkResult
import tarikdev.app.testproject.network.NetworkRepository
import tarikdev.app.testproject.network.RetrofitService

class CommentsViewModel: ViewModel() {

    val commentsResult = MutableLiveData<NetworkResult<List<Comment>>>()

    private val repository = NetworkRepository(RetrofitService.getApi())

    fun getAllComments() = liveData(Dispatchers.IO) {
        emit(NetworkResult.loading(data = null))
        try {
            emit(NetworkResult.success(data = repository.getAllComments()))
        } catch (exception: Exception) {
            emit(NetworkResult.error(data = null, message = exception.message ?: "Error to get all comment"))
        }
    }

}