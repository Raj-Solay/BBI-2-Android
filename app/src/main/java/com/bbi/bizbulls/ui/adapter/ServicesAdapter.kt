package com.bbi.bizbulls.ui.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R
import com.bbi.bizbulls.ui.registrationforfo.FoRegistrationDashBoardActivity
import com.bbi.bizbulls.utils.CommonUtils

class ServicesAdapter(activity: FragmentActivity) :
    RecyclerView.Adapter<ServicesAdapter.viewHolder>() {
    var context: Context
    var iconsName = arrayOf(
        "Food Delivery",
        "Genie Service",
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
            CommonUtils.showServiceDialog(context)
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