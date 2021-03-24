package tarikdev.app.testproject.ui.second.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.vh_comments.view.*
import tarikdev.app.testproject.model.Comment

class CommentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: Comment) {
        itemView.apply {
            comment_id_text.text = "Comment #${comment.id.toString()} (post #${comment.postId})"
            name_text.text = "${comment.name} (${comment.email})"
            body_text.text = comment.body
        }

    }


}