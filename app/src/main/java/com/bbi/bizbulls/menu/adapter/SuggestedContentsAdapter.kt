package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R

class SuggestedContentsAdapter() :
    RecyclerView.Adapter<SuggestedContentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedContentsAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.social_invite_adapter, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: SuggestedContentsAdapter.ViewHolder, position: Int) {
    // holder.tv_cat_name.text=linkList.get(position)
        holder.tv_invite.visibility=View.VISIBLE
    }
    override fun getItemCount(): Int {
        return  4;
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_cat_name = itemView.findViewById(R.id.tv_cat_name) as TextView
        var ll_row = itemView.findViewById(R.id.ll_row) as LinearLayout
        var tv_invite = itemView.findViewById(R.id.tv_invite) as TextView
    }
}