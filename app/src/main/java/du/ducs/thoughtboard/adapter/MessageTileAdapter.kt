package du.ducs.thoughtboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import du.ducs.thoughtboard.HomeScreenFragment
import du.ducs.thoughtboard.R
import du.ducs.thoughtboard.model.Message


class ItemAdapter(
    private val context: HomeScreenFragment,
    private val dataset: List<Message>
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Message object.
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val message: TextView = view.findViewById(R.id.message)
        val author: TextView = view.findViewById(R.id.author)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_tile, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.title.text = context.resources.getString(item.titleResourceId)
        holder.message.text = context.resources.getString(item.messageResourceId)
        holder.author.text = context.resources.getString(item.authorResourceId)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}
