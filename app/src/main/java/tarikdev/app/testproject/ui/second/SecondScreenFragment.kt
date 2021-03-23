package tarikdev.app.testproject.ui.second

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            Log.e(TAG, "onActivityCreated: ", )
            if (it.status == NetworkResult.Status.SUCCESS) adapter.update(it.data!!)
        })
    }

}