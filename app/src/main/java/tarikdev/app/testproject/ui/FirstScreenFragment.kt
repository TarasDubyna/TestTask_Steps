package tarikdev.app.testproject.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first_screen.*
import tarikdev.app.testproject.R

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
        viewModel.getAllComments().observe(viewLifecycleOwner, {
            Log.e(TAG, "onActivityCreated: ${it.status.name}")
        })


        get_comments_btn.setOnClickListener {
            validateInputData()?.let {
                //viewModel.getComments(it.first, it.second)
                //viewModel.getAllComments().observe(this, {})
                //findNavController().navigate(R.id.action_firstScreenFragment_to_secondScreenFragment)
                findNavController().navigate(R.id.secondScreenFragment)
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