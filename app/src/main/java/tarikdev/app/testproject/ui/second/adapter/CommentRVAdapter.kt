package tarikdev.app.testproject.ui.second.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tarikdev.app.testproject.R
import tarikdev.app.testproject.model.Comment

class CommentRVAdapter: RecyclerView.Adapter<CommentVH>() {

    private val comments: MutableList<Comment> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentVH =
        CommentVH(LayoutInflater.from(parent.context).inflate(R.layout.vh_comments, parent, false))


    override fun onBindViewHolder(holder: CommentVH, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size

    fun addComments(comments: List<Comment>) {
        val updFirstPos = comments.lastIndex
        this.comments.addAll(comments)
        notifyItemRangeInserted(updFirstPos, this.comments.lastIndex)
    }

    fun update(comments: List<Comment>) {
        this.comments.clear()
        this.comments.addAll(comments)

        this.notifyDataSetChanged()
    }


}