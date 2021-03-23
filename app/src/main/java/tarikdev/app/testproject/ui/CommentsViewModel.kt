package tarikdev.app.testproject.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tarikdev.app.testproject.model.Comment

class CommentsViewModel: ViewModel() {

    val comments = MutableLiveData<Comment>()

    fun getComments(fromId: Int, toId: Int) {

    }

}