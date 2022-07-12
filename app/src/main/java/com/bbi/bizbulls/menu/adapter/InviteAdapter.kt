package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R

class InviteAdapter() : RecyclerView.Adapter<InviteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InviteAdapter.ViewHolder {
        val v =LayoutInflater.from(parent.context).inflate(R.layout.invite_adapter, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: InviteAdapter.ViewHolder, position: Int) {
    }
    override fun getItemCount(): Int {
        return  10;
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}