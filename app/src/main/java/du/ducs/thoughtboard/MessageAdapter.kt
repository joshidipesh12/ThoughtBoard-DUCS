package du.ducs.thoughtboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(var context: Context):RecyclerView.Adapter<MessageAdapter.ViewHolder>()
{
    var dataList= emptyList<DataModel>()
    class ViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView) {


        init {
        }
    }

    internal fun setDataList(dataList:List<DataModel>)
    {
        this.dataList=dataList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        TODO("Not yet implemented")
        var view =LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")

        var data=dataList[position]

    }

    override fun getItemCount(): Int=dataList.size



}