package tarikdev.app.testproject.ui.first

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first_screen.*
import tarikdev.app.testproject.R
import tarikdev.app.testproject.model.NetworkResult
import tarikdev.app.testproject.ui.CommentsViewModel

class FirstScreenFragment : Fragment() {

    companion object {
        fun newInstance() = FirstScreenFragment()

        val TAG = FirstScreenFragment::class.java.simpleName

    }

    private lateinit var viewModel: CommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CommentsViewModel::class.java)
        viewModel.commentsResult.observe(viewLifecycleOwner, {
            Log.e(TAG, "getAllComments(): status=${it.status.name}")
            when (it.status) {
                NetworkResult.Status.SUCCESS -> {
                    loading_pb.visibility = INVISIBLE
                    error_text.visibility = INVISIBLE
                    findNavController().navigate(R.id.action_firstScreenFragment_to_secondScreenFragment)
                }
                NetworkResult.Status.ERROR -> {
                    loading_pb.visibility = INVISIBLE
                    error_text.visibility = VISIBLE
                    error_text.text = it.message
                }
                NetworkResult.Status.LOADING -> {
                    loading_pb.visibility = VISIBLE
                    error_text.visibility = INVISIBLE
                }
            }
        })

        get_comments_btn.setOnClickListener {
            viewModel.getComments()
            validateInputData()?.let {
                viewModel.getComments()
            }
        }

    }

    private fun validateInputData(): Pair<Int, Int>? {

        if (lower_bound_et.text.isNullOrEmpty() && higher_bound_et.text.isNullOrEmpty()) {
            Log.e(TAG, "validateInputData: lower and higher - empty")
            Snackbar.make(lower_bound_et, "Lower and Higher bound - empty!", Snackbar.LENGTH_SHORT).show()
            return null
        } else if (lower_bound_et.text.isNullOrEmpty()) {
            Log.e(TAG, "validateInputData: lower - empty")
            Snackbar.make(lower_bound_et, "Lower bound - empty!", Snackbar.LENGTH_SHORT).show()
            return null
        } else if (higher_bound_et.text.isNullOrEmpty()) {
            Log.e(TAG, "validateInputData: higher - empty")
            Snackbar.make(higher_bound_et, "Higher bound - empty!", Snackbar.LENGTH_SHORT).show()
            return null
        } else {

            val lower = lower_bound_et.text.toString().toInt()
            val higher = higher_bound_et.text.toString().toInt()

            return if (higher <= lower) {
                Log.e(TAG, "validateInputData: lower=$lower, higher=$higher - wrong validation, lower > higher")
                Snackbar.make(higher_bound_et, "Higher bound should be higher then lower bound", Snackbar.LENGTH_SHORT).show()
                Pair(lower, higher)
            } else {
                Log.d(TAG, "validateInputData: lower=$lower, higher=$higher")
                null
            }

        }
    }



}