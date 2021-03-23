package tarikdev.app.testproject.ui.second.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.vh_comments.view.*
import tarikdev.app.testproject.model.Comment

class CommentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: Comment) {
        itemView.post_id_text.text = comment.postId.toString()
        itemView.id_text.text = comment.id.toString()
        itemView.name_text.text = comment.name
        itemView.email_text.text = comment.email
        itemView.body_text.text = comment.body
    }


}