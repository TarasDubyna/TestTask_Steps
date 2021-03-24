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
import tarikdev.app.testproject.model.NetworkResult
import tarikdev.app.testproject.ui.CommentsViewModel
import tarikdev.app.testproject.ui.second.adapter.CommentRVAdapter


class SecondScreenFragment : Fragment() {

    companion object {
        fun newInstance() = SecondScreenFragment()
        val TAG = SecondScreenFragment::class.java.simpleName
    }

    private lateinit var viewModel: CommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CommentRVAdapter()
        comments_rv.layoutManager = LinearLayoutManager(context)
        comments_rv.adapter = adapter

        viewModel = ViewModelProvider(requireActivity()).get(CommentsViewModel::class.java)
        viewModel.commentsResult.observe(viewLifecycleOwner, {
            if (it.status == NetworkResult.Status.SUCCESS) {
                Log.d(TAG, "commentsResult.observe: SUCCESS")
                adapter.addComments(it.data!!)
            }
        })

        var isLoading = true
        val recyclerCallback = object : Paginate.Callbacks {
            override fun onLoadMore() {
                Log.d(TAG, "onLoadMore: ")
                isLoading = viewModel.getCommentsNextPage()
            }

            override fun isLoading(): Boolean {
                val isLastPage = viewModel.rangePages.lastIndex == viewModel.getCurrentPage()
                Log.d(TAG, "isLoading: isLastPage=${!isLastPage}")
                //return !isLastPage
                return viewModel.getCommentsNextPage()
            }

            override fun hasLoadedAllItems(): Boolean {
                // Indicate whether all data (pages) are loaded or not
                val isLastPage = viewModel.rangePages.lastIndex == viewModel.getCurrentPage()
                Log.d(TAG, "hasLoadedAllItems: isLastPage=$isLastPage")
                return isLastPage
            }

        }


        Paginate.with(comments_rv, recyclerCallback)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(true)
            .setLoadingListItemCreator(LoadingListItemCreator.DEFAULT)
            .build()

    }

}