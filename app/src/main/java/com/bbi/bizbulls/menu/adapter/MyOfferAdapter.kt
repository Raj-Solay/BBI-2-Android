package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R
import java.util.*


class MyOfferAdapter() : RecyclerView.Adapter<MyOfferAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_offer_adapter, parent, false))

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MyOfferAdapter.ViewHolder, position: Int) {
        holder.bindItems()
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return 10
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems() {

        }
    }
}