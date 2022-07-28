package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.biz.bizbulls.R

class PaymentMethodAdapter(/*val linkList: ArrayList<String>*/) :
    RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.payment_method_adapter, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: PaymentMethodAdapter.ViewHolder, position: Int) {
    // holder.tv_cat_name.text=linkList.get(position)
    }
    override fun getItemCount(): Int {
        return  3
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_cat_name = itemView.findViewById(R.id.tv_cat_name) as TextView
        var ll_row = itemView.findViewById(R.id.ll_row) as LinearLayout
    }
}