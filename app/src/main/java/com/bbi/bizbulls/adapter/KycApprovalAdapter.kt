package com.foldio.android.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.FilterActivity
import com.bbi.bizbulls.KycDocViewActivity
import com.bbi.bizbulls.R
import com.bbi.bizbulls.model.PersonalUserAll

class KycApprovalAdapter(var userList: List<PersonalUserAll.Data>) :
    RecyclerView.Adapter<KycApprovalAdapter.ViewHolder>() {
    private var context: Context? = null;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KycApprovalAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.kyc_approval_adapter, parent, false)
        context = parent.context;
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: KycApprovalAdapter.ViewHolder, position: Int) {
     holder.txtName.text=userList.get(position).fullname
     holder.txtAddress.text=userList.get(position).permanentAdd.toString()
     holder.txtDate.text=userList.get(position).updatedAt.toString()
        if(!userList.get(position).emailid.toString().isNullOrEmpty()){
            holder.txtFullname.text=userList.get(position).emailid.toString()
        }
        holder.txtView.setOnClickListener {
            val intent = Intent(context, KycDocViewActivity::class.java)
            intent.putExtra("model",userList.get(position))
            context?.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return  userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName = itemView.findViewById(R.id.txtName) as TextView
        var txtAddress = itemView.findViewById(R.id.txtAddress) as TextView
        var txtDate = itemView.findViewById(R.id.txtDate) as TextView
        var txtFullname = itemView.findViewById(R.id.txtFullname) as TextView
        var txtView = itemView.findViewById(R.id.txtView) as TextView
    }
}