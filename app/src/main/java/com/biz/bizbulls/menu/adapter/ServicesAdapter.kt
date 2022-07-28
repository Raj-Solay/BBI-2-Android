package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.biz.bizbulls.R

class ServicesAdapter(val linkList: ArrayList<String>) :
    RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesAdapter.ViewHolder {
        val v =LayoutInflater.from(parent.context).inflate(R.layout.services_adapter, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ServicesAdapter.ViewHolder, position: Int) {
     holder.tv_cat_name.text=linkList.get(position)
    }
    override fun getItemCount(): Int {
        return  linkList.size;
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_cat_name = itemView.findViewById(R.id.tv_cat_name) as TextView
        var ll_row = itemView.findViewById(R.id.ll_row) as CardView
    }
}