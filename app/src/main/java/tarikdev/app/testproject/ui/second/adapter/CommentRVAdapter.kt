package tarikdev.app.testproject.ui.second.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tarikdev.app.testproject.R
import tarikdev.app.testproject.model.Comment
import tarikdev.app.testproject.ui.CommentsViewModel

class CommentRVAdapter(val comments: MutableList<Comment>): RecyclerView.Adapter<CommentVH>() {

    companion object {
        val TAG = CommentRVAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentVH =
        CommentVH(LayoutInflater.from(parent.context).inflate(R.layout.vh_comments, parent, false))


    override fun onBindViewHolder(holder: CommentVH, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size

    fun addComments(comments: List<Comment>) {
        val positionStart = this.comments.size
        this.comments.addAll(comments)
        notifyItemRangeInserted(positionStart, comments.size)
    }



}