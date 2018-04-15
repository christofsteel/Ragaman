package org.opensomething.ragaman

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.search_item.view.*
import org.opensomething.ragaman.R.id.textView


interface OnItemClickListener {
    fun onItemClicked(position: Int, view: View)
}



class SearchAdapter(private val dataset: ArrayList<Pair<Point, String>>) :
        RecyclerView.Adapter<SearchAdapter.ViewHolder>()  {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {
        private var pview: View = view
        var point: Point? = null

        init {
            pview.setOnLongClickListener(this)
        }
        override fun onLongClick(v: View) : Boolean {
            val context = v.context
            Toast.makeText(context, point?.name, Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:0,0?q=${point?.lat},${point?.lon}(${point?.name})")
            if(intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                return true
            }
            return false
        }


    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SearchAdapter.ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_item, parent, false) as View
        // set the view's size, margins, paddings and layout parameters
        // ...
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.layout.textView.text = dataset[position].first.name
        holder.point = dataset[position].first
        when(dataset[position].second) {
            "stop" -> holder.view.layout.imageView.setImageResource(R.drawable.ic_stop)
            "street" -> holder.view.layout.imageView.setImageResource(R.drawable.ic_street)
            "poi" -> holder.view.layout.imageView.setImageResource(R.drawable.ic_point_of_interest)
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size


}