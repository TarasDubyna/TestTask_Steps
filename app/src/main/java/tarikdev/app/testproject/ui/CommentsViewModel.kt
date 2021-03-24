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
import tarikdev.app.testproject.ui.second.adapter.CommentRVAdapter

class CommentsViewModel: ViewModel() {
    
    companion object {
        val TAG = CommentsViewModel::class.java.simpleName
    }

    private val repository = NetworkRepository(RetrofitService.getApi())

    val commentsResult = MutableLiveData<NetworkResult<List<Comment>>>()

    val currentPage = MutableLiveData<Int>()
    fun getPageCount(): Int = rangePages.size

    private val rangePages: MutableList<Pair<Int, Int>> = mutableListOf()
    fun getPageRange(page: Int): Pair<Int, Int> = rangePages[page]
    fun getCurrentPageRange(): Pair<Int, Int> = rangePages[currentPage.value?: 0]


    /*fun getComments(fromId: Int, toId: Int) {
        Log.d(TAG, "getComments: fromId=$fromId, toId=$toId")
        load(fromId, toId)
    }*/

    fun getComments(page: Int) {

        if (commentsResult.value?.status == NetworkResult.Status.LOADING) return

        val pageRange = getPageRange(page)
        Log.d(TAG, "getComments: page=$page, range=${pageRange}")
        load(pageRange.first, pageRange.second)
    }

    private fun load(from: Int, to: Int) {

        commentsResult.value = NetworkResult.loading()

        repository.getComments(from, to).enqueue(object : Callback<List<Comment>> {

            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {

                Log.e(TAG, "getComments: onResponse: ${response.body()!!.map { it.id }.toString()}")

                commentsResult.value =
                    if (response.body() == null) NetworkResult.success(data = listOf())
                    else NetworkResult.success(data = response.body()!!.sortedBy { it.id })

                val newPage = currentPage.value?.plus(1)
                currentPage.value = newPage
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.e(TAG, "getComments: onFailure: ${t.message}")
                commentsResult.value = NetworkResult.error(t.message.toString())
            }

        })
    }

    var range: Pair<Int, Int> = Pair(0, 0)
    fun initRange(fromId: Int, toId: Int) {
        Log.d(TAG, "changeRange: [$fromId,$toId], set currentPage=0")
        range = Pair(fromId, toId)

        currentPage.value = 0

        rangePages.clear()
        rangePages.addAll(
            (fromId..toId step 10).map {
                val from = it
                val to = if (it + 9 > toId) toId else it + 9
                Pair(from, to)
            })
        Log.d(TAG, "changeRange: rangePages=${rangePages.toString()}")
    }

}