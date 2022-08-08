package com.biz.bizbulls.ui.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.biz.bizbulls.R
import com.biz.bizbulls.utils.CommonUtils.showCustomToast

class AdminUploadServiceAdapter(activity: FragmentActivity) :
    RecyclerView.Adapter<AdminUploadServiceAdapter.viewHolder>() {
    var context: Context
    var iconsName = arrayOf(
        "Upload Excel",
        "Upload Discount",
        "Book a Ride",
        "Maintenance Boy",
        "Property Pasand",
        "Doctor appointment"
    )
    var icons = intArrayOf(
        R.drawable.delivery,
        R.drawable.g_services,
        R.drawable.cab,
        R.drawable.maintaince,
        R.drawable.property_broker,
        R.drawable.schedule
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.itemlist, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.textView.text = iconsName[position]
        holder.imageView.setImageResource(icons[position])
        holder.itemView.setOnClickListener { v: View? ->
            //CommonUtils.showServiceDialog(context)
            if(position == 0){
                Log.d("Excel","Upload...")
            }else{
                Toast(context).showCustomToast(
                    "Currently your location is not registered but we are launching in your area soon.\n" +
                            "\n" +
                            "Please visit us again.", context as Activity
                )
            }

        }

    }


    override fun getItemCount(): Int {
        return iconsName.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: AppCompatImageView
        var textView: TextView

        init {
            imageView = itemView.findViewById(R.id.iv_top_small_card)
            textView = itemView.findViewById(R.id.tv_top_small_card)
        }
    }

    init {
        context = activity
    }
}