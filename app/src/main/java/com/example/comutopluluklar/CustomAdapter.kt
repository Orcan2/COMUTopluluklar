package com.example.comutopluluklar

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class CustomAdapter(private val toplulukList: ArrayList<Topluluk>, val context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvtopisim: TextView = view.findViewById(R.id.tvTopAd)
        val tvtopaciklama: TextView = view.findViewById(R.id.tvTopAciklama)
        val tvtopID=view.findViewById<TextView>(R.id.tvID)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.topluluk_item_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val topluluk = toplulukList[position]
        viewHolder.tvtopisim.text = topluluk.toplulukadi
        viewHolder.tvtopaciklama.text = topluluk.toplulukaciklama
        viewHolder.tvtopID.text=topluluk.id

        viewHolder.itemView.setOnClickListener {

            val toplulukadi = topluluk.toplulukadi
            val toplulukAciklamasi = topluluk.toplulukaciklama
            val toplulukID = topluluk.id
            val intent = Intent(context, DetayActivity::class.java)


            intent.putExtra("putAd", toplulukadi)
            intent.putExtra("putAciklama", toplulukAciklamasi)
            intent.putExtra("putID", toplulukID)
            context.startActivity(intent)

        }
    }

    override fun getItemCount() = toplulukList.size
}

//package com.example.comutopluluklar
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//
//class CustomAdapter(private val toplulukList: ArrayList<Topluluk>,val context: Context) :
//    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val tvtopisim: TextView=view.findViewById(R.id.tvTopAd)
//        val tvtopaciklama: TextView=view.findViewById(R.id.tvTopAciklama)
//    }
//
//    // Create new views (invoked by the layout manager)
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        // Create a new view, which defines the UI of the list item
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.topluluk_item_list, viewGroup, false)
//
//        return ViewHolder(view)
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//
//        // Get element from your dataset at this position and replace the
//        // contents of the view with that element
//        viewHolder.tvtopisim.text = toplulukList[position].toplulukadi
//        viewHolder.tvtopaciklama.text = toplulukList[position].toplulukaciklama
//        viewHolder.itemView.setOnClickListener {
//            //Listede tıklanan eleman icin secili pozisyonu al
//            var topluluk = toplulukList[position]
//            var toplulukID = topluluk.toplulukId
//            var toplulukadi: String? = topluluk.toplulukadi
//            var toplulukAciklamasi: String? = topluluk.toplulukaciklama
//            //Secili elemanın bilgilerini detay sayfasına aktar
//            var intent = Intent(context, DetayActivity::class.java)
//            Log.d("anannnnnnnnnnnnn", "toplulukID: $toplulukID")
//            intent.putExtra("putToplulukID", toplulukID)
//            intent.putExtra("putAd", toplulukadi)
//            intent.putExtra("putAciklama", toplulukAciklamasi)
//            //Activity sayfasını baslat
//            context.startActivity(intent)
//        }
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount() = toplulukList.size
//
//}
