package com.bbi.bizbulls.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.CommonUtils.showCustomToast

class FranchiseeAdapter(activity: FragmentActivity) :
    RecyclerView.Adapter<FranchiseeAdapter.viewHolder>() {
    var context: Context
    var iconsName = arrayOf(
        "Personal",
        "Business",
        "Home",
        "Vehicle",
        "Education",
        "Mortgage")
    var icons = intArrayOf(
        R.drawable._loaning,
        R.drawable._conttraining,
        R.drawable.property_broker,
        R.drawable.delivery,
        R.drawable._conttraining,
        R.drawable._technologyenable,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_franchiese, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.textView.text = iconsName[position]
        holder.imageView.setImageResource(icons[position])
        holder.itemView.setOnClickListener { v: View? ->
            Toast(context).showCustomToast(

                "Currently your location is not registered but we are launching in your area soon.\n" +
                        "\n" +
                        "Please visit us again.", context as Activity
            )
        }
    }

    override fun getItemCount(): Int {
        return iconsName.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
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