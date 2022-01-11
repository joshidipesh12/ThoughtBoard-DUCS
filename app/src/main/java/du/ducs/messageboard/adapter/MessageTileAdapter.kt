package du.ducs.messageboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import du.ducs.messageboard.HomeScreenFragmentDirections
import du.ducs.messageboard.R
import du.ducs.messageboard.model.Message
import java.text.SimpleDateFormat
import java.util.*

class MessageTileAdapter :
    ListAdapter<Message, MessageTileAdapter.MessageViewHolder>(DiffCallback) {

    private val formatter = SimpleDateFormat("h:mm a", Locale.ENGLISH)

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldMsg: Message, newMsg: Message): Boolean {
                return oldMsg === newMsg
            }

            override fun areContentsTheSame(oldMsg: Message, newMsg: Message): Boolean {
                return oldMsg.id == newMsg.id
            }
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Message object.
    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.author)
        val time: TextView = view.findViewById(R.id.time)
        val message: TextView = view.findViewById(R.id.message)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)

        return MessageViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = message.timestamp
        holder.message.text = message.message
        holder.author.text = message.userId
        holder.time.text = formatter.format(calendar.time)
    }

}
