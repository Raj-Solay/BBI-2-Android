package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biz.bizbulls.R

class FollowerAdapter(/*val linkList: ArrayList<String>*/) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.follower_adapter, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: FollowerAdapter.ViewHolder, position: Int) {
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