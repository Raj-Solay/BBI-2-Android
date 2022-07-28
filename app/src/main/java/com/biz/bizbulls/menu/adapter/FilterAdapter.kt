package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.biz.bizbulls.R


class FilterAdapter(var indusstry: Array<String>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.filter_adapter, parent, false))

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        holder.bindItems(indusstry.get(position))
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return indusstry.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(text: String) {
   var checkBox=itemView.findViewById(R.id.checkBox) as CheckBox
            checkBox.text=text

        }
    }
}