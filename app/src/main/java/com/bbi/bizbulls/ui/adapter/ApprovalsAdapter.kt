package com.bbi.bizbulls.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.KycListActivity
import com.bbi.bizbulls.R
import com.bbi.bizbulls.utils.CommonUtils

class ApprovalsAdapter(var activity: FragmentActivity) :
    RecyclerView.Adapter<ApprovalsAdapter.viewHolder>() {
    var context: Context
    var iconsName = arrayOf(
        "KYC",
        "Location",
        "Agreements",
        "Set-Up",
        "License"
    )
    var icons = intArrayOf(
        R.drawable.kyc,
        R.drawable.ic__location,
        R.drawable.agreement,
        R.drawable.facility_arrangement,
        R.drawable.ic_certificate
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.itemlist_approval, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.textView.text = iconsName[position]
        holder.imageView.setImageResource(icons[position])
        holder.itemView.setOnClickListener { v: View? ->
            val intent = Intent(activity, KycListActivity::class.java)
            intent.putExtra("APPROVAL_TYPE",position)
            activity.startActivity(intent)

          //  CommonUtils.showServiceDialog(context)
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