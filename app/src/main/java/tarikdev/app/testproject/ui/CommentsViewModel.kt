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
import tarikdev.app.testproject.ui.first.FirstScreenFragment

class CommentsViewModel: ViewModel() {
    
    companion object {
        val TAG = CommentsViewModel::class.java.simpleName
    }

    private val repository = NetworkRepository(RetrofitService.getApi())

    private var currentPage: Int = 0
    fun getCurrentPage(): Int = currentPage
    val rangePages: MutableList<Pair<Int, Int>> = mutableListOf()

    val commentsResult = MutableLiveData<NetworkResult<List<Comment>>>()

    fun setCommentsRange(fromId: Int, toId: Int) {
        Log.d(TAG, "setCommentsRange: fromId=$fromId, toId=$toId")

        this.currentPage = 0

        rangePages.clear()
        rangePages.addAll(
            (fromId..toId step 10).map {
                val from = it
                val to = if (it + 9 > toId) toId else it + 9
                Pair(from, to)
            })
    }

    fun getCommentsFirstPage() {
        currentPage = 0
        val range = rangePages[currentPage]
        load(range.first, range.second)
    }

    fun getCommentsNextPage(): Boolean {
        val page = currentPage + 1
        if (page >= rangePages.lastIndex) return false

        val range = rangePages[page]
        Log.e(TAG, "getCommentsNextPage: nextPage=$page, maxPage=${rangePages.lastIndex}, range=[${range.first},${range.second}]")

        load(range.first, range.second)

        return true

    }

    private fun load(from: Int, to: Int) {
        commentsResult.value = NetworkResult.loading(data = null)

        repository.getComments(from, to).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                Log.e(TAG, "onResponse: ")
                commentsResult.value =
                    if (response.body() == null) NetworkResult.error(
                        data = null,
                        "Response body - null"
                    )
                    else {
                        currentPage++
                        NetworkResult.success(data = response.body()!!)
                    }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.e(TAG, "onFailure: ")
                commentsResult.value = NetworkResult.error(data = null, t.message.toString())
            }
        })
    }

    /*fun getComments(page: Int) {

        var from = (page - 1) * 10 + 1
        var to = page * 10

        Log.e(TAG, "getComments: fromId=$fromId, toId=$toId, page=$page")



        commentsResult.value = NetworkResult.loading(data = null)

        repository.getComments(fromId, toId, page).enqueue(object : Callback<List<Comment>> {
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

    }*/

/*
    fun getAllComments() {

        Log.e(TAG, "getAllComments: ", )
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
    }*/


}