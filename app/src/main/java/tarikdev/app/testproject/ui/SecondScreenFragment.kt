package tarikdev.app.testproject.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tarikdev.app.testproject.R

class SecondScreenFragment : Fragment() {

    companion object {
        fun newInstance() = SecondScreenFragment()
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

        viewModel = ViewModelProvider(requireActivity()).get(CommentsViewModel::class.java)
        viewModel.commentsResult.observe(viewLifecycleOwner, {

        })
    }

}