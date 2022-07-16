package com.foldio.android.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.DocViewListener
import com.bbi.bizbulls.FilterActivity
import com.bbi.bizbulls.KycDocViewActivity
import com.bbi.bizbulls.R
import com.bbi.bizbulls.model.ApprovalDocRes
import com.bbi.bizbulls.model.PersonalUserAll
import com.bbi.bizbulls.utils.Globals
import com.squareup.picasso.Picasso

class DocApprovalAdapter(var userList: List<ApprovalDocRes.Data>?,var docViewListener: DocViewListener) :
    RecyclerView.Adapter<DocApprovalAdapter.ViewHolder>() {
    private var context: Context? = null;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocApprovalAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.doc_approval_adapter,
                parent, false)
        context = parent.context;
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: DocApprovalAdapter.ViewHolder, position: Int) {
     holder.txtDocName.text="Document Id or Name : "+userList!!.get(position).id+"\nDocuemtn Type : "+ userList!!.get(position).documentType
        Picasso.get().load(userList!!.get(position).documentName)
            .placeholder(R.drawable.img_default)
            .into(holder.imgDocView)
        holder.txtVerify.setOnClickListener {
            docViewListener.onDocView(userList!!.get(position))
        }
        if(userList!!.get(position).isApproved || userList!!.get(position).documentStatus == "1"){
            holder.txtVerify.setText("Approved")
        }else{
            holder.txtVerify.setText("Verify")
        }
    }
    override fun getItemCount(): Int {
        return  userList!!.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgDocView = itemView.findViewById(R.id.imgDocView) as ImageView
        var txtDocName = itemView.findViewById(R.id.txtDocName) as TextView
        var txtVerify = itemView.findViewById(R.id.txtVerify) as TextView
       // var txtApproved = itemView.findViewById(R.id.txtApproved) as TextView

    }
}