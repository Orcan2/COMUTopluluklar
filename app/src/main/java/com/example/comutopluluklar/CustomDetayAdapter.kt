package com.example.comutopluluklar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomDetayAdapter(private val gonderiList: ArrayList<ToplulukGonderi>, val context: Context) :
    RecyclerView.Adapter<CustomDetayAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvyenigonderi: TextView=view.findViewById(R.id.yenigonderitv)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.yenigondericardview, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = gonderiList[position]

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvyenigonderi.text = currentItem.yenigonderi

        viewHolder.itemView.setOnClickListener {
            //Listede tıklanan eleman icin secili pozisyonu al
            var gonderi = gonderiList.get(position)



        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = gonderiList.size

}
