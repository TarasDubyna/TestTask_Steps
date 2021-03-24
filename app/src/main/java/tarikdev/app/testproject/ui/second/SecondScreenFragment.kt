package tarikdev.app.testproject.ui.second

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import com.paginate.recycler.LoadingListItemSpanLookup
import kotlinx.android.synthetic.main.fragment_second_screen.*
import tarikdev.app.testproject.R
import tarikdev.app.testproject.model.Comment
import tarikdev.app.testproject.model.NetworkResult
import tarikdev.app.testproject.ui.CommentsViewModel
import tarikdev.app.testproject.ui.second.adapter.CommentRVAdapter


class SecondScreenFragment : Fragment() {

    companion object {
        fun newInstance() = SecondScreenFragment()
        val TAG = SecondScreenFragment::class.java.simpleName
    }

    private lateinit var viewModel: CommentsViewModel
    private lateinit var adapter: CommentRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var isLoading: Boolean = false


        viewModel = ViewModelProvider(requireActivity()).get(CommentsViewModel::class.java)
        viewModel.commentsResult.observe(viewLifecycleOwner, { response ->

            isLoading = response.status == NetworkResult.Status.LOADING

            if (response.status == NetworkResult.Status.SUCCESS) {
                response.data?.let { comments ->
                    if (!this::adapter.isInitialized) {

                        Log.d(TAG, "commentsResult.observe: init adapter: " +
                                "ids=[${comments.first().id},${comments.last().id}], " +
                                "page[${viewModel.currentPage.value}][${viewModel.getPageCount()}]"
                        )

                        initAdapter(comments.toMutableList())

                    } else {
                        Log.d(TAG, "commentsResult.observe: add comments:" +
                                "ids=[${comments.first().id},${comments.last().id}], " +
                                "page[${viewModel.currentPage.value}][${viewModel.getPageCount()}]"
                        )
                        adapter.addComments(comments)
                    }

                }
            }
        })



    }

    private fun initAdapter(comments: MutableList<Comment>) {

        adapter = CommentRVAdapter(comments)
        comments_rv.layoutManager = LinearLayoutManager(context)
        comments_rv.adapter = adapter

        val recyclerCallback = object : Paginate.Callbacks {

            override fun onLoadMore() {
                // Load next page of data (e.g. network or database)
                viewModel.currentPage.value?.let {
                    if (viewModel.getPageCount() != it) {
                        val range = viewModel.getPageRange(it)
                        Log.d(TAG, "recyclerCallback.onLoadMore: nextPage=$it, range[${range.first},${range.second}]")
                        viewModel.getComments(it)
                    }
                }
            }

            override fun isLoading(): Boolean {
                // Indicate whether new page loading is in progress or not
                //Log.d(TAG, "recyclerCallback.isLoading: $isLoading")

                return viewModel.getPageCount() == viewModel.currentPage.value
            }

            override fun hasLoadedAllItems(): Boolean {
                // Indicate whether all data (pages) are loaded or not
                val isLoadedAllItems = adapter.comments.lastOrNull()?.id ?: 0 == viewModel.range.second
                //val isLoadedAllItems = viewModel.getPageCount() == viewModel.currentPage.value
                Log.d(
                    TAG,
                    "recyclerCallback.hasLoadedAllItems: $isLoadedAllItems, currentPage=${viewModel.currentPage.value}, allPages=${viewModel.getPageCount()}"
                )
                return isLoadedAllItems
            }

        }

        Paginate.with(comments_rv, recyclerCallback)
            .setLoadingTriggerThreshold(1)
            .addLoadingListItem(true)
            .setLoadingListItemCreator(LoadingListItemCreator.DEFAULT)
            .build()
    }


}