package com.bbi.bizbulls.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R

class DatesAdapter(var context: Context, academic_List: MutableList<String>) :
    RecyclerView.Adapter<DatesAdapter.viewHolder>() {
    var academicList: List<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dates, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val aceAcademic = academicList[position]
        holder.txtDate.text = aceAcademic
    }

    override fun getItemCount(): Int {
        return academicList.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtDate: AppCompatTextView
        init {
            txtDate = itemView.findViewById(R.id.txtDate)
        }
    }

    init {
        academicList = academic_List
    }
}