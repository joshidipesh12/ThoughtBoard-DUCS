package du.ducs.thoughtboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import du.ducs.thoughtboard.R
import du.ducs.thoughtboard.model.Message

class MessageTileAdapter: ListAdapter<Message, MessageTileAdapter.MessageViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldMsg: Message, newMsg: Message): Boolean {
                return oldMsg === newMsg
            }

            override fun areContentsTheSame(oldMsg: Message, newMsg: Message): Boolean {
                return oldMsg.title == newMsg.title
                        && oldMsg.message == newMsg.message
                        && oldMsg.userId == oldMsg.userId
            }
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Message object.
    class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val message: TextView = view.findViewById(R.id.message)
        val author: TextView = view.findViewById(R.id.author)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_tile, parent, false)

        return MessageViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.title.text   = message.title
        holder.message.text = message.message
        holder.author.text  = message.userId
    }
}
