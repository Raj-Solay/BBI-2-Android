package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R

class AproveStatusAdapter(/*val linkList: ArrayList<String>*/) :
    RecyclerView.Adapter<AproveStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AproveStatusAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.aprove_status_adapter, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: AproveStatusAdapter.ViewHolder, position: Int) {
    // holder.tv_cat_name.text=linkList.get(position)
    }
    override fun getItemCount(): Int {
        return  10
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var tv_cat_name = itemView.findViewById(R.id.tv_cat_name) as TextView
      //  var ll_row = itemView.findViewById(R.id.ll_row) as LinearLayout
    }
}