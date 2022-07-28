package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biz.bizbulls.R

class PaymentSettingAdapter(/*val linkList: ArrayList<String>*/) :
    RecyclerView.Adapter<PaymentSettingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentSettingAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.payment_setting_adapter, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: PaymentSettingAdapter.ViewHolder, position: Int) {
    // holder.tv_cat_name.text=linkList.get(position)
    }
    override fun getItemCount(): Int {
        return  1
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var tv_cat_name = itemView.findViewById(R.id.tv_cat_name) as TextView
      //  var ll_row = itemView.findViewById(R.id.ll_row) as LinearLayout
    }
}