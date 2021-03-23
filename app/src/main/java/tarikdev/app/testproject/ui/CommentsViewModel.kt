package tarikdev.app.testproject.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tarikdev.app.testproject.model.Comment
import tarikdev.app.testproject.model.NetworkResult
import tarikdev.app.testproject.network.NetworkRepository
import tarikdev.app.testproject.network.RetrofitService

class CommentsViewModel: ViewModel() {
    
    companion object {
        val TAG = CommentsViewModel::class.java.simpleName
    }

    private val repository = NetworkRepository(RetrofitService.getApi())

    val commentsResult = MutableLiveData<NetworkResult<List<Comment>>>()

    fun getComments() {

        Log.e(TAG, "getComments: ", )
        commentsResult.value = NetworkResult.loading(data = null)

        repository.getAllComments().enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                Log.e(TAG, "onResponse: ")
                commentsResult.value =
                    if (response.body() == null) NetworkResult.error(
                        data = null,
                        "Response body - null"
                    )
                    else NetworkResult.success(data = response.body()!!)
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.e(TAG, "onFailure: ")
                commentsResult.value = NetworkResult.error(data = null, t.message.toString())
            }
        })
    }


}