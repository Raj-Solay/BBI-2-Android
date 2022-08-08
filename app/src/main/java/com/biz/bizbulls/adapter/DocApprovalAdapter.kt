package com.biz.bizbulls.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.biz.bizbulls.DocViewListener
import com.biz.bizbulls.R
import com.biz.bizbulls.model.ApprovalDocRes
import com.squareup.picasso.Picasso

class DocApprovalAdapter(
    var userList: List<ApprovalDocRes.Data>?,
    var docViewListener: DocViewListener,
    var approval_type: Int
) :
    RecyclerView.Adapter<DocApprovalAdapter.ViewHolder>() {
    private var context: Context? = null;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.doc_approval_adapter,
                parent, false)
        context = parent.context;
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var documentName  =""
        if(userList!!.get(position).documentId == "1"){
            documentName = "Pan Card"
        }else if(userList!!.get(position).documentId == "2"){
            documentName = "Aadhaar Card"
        }else if(userList!!.get(position).documentId == "3"){
            documentName = "Residential Address Proof"
        }else if(userList!!.get(position).documentId == "4"){
            documentName = "Recent Photograph of applicant"
        }else if(userList!!.get(position).documentId == "5"){
            documentName = "Individually filled &amp; signed copies of this form (in case of partnership)"
        }else if(userList!!.get(position).documentId == "6"){
            documentName = "BIZ BULLS Arbitrary Agreement"
        }
      holder.txtDocName.text = ""+documentName
        Picasso.get().load(userList!!.get(position).documentName)
            .placeholder(R.drawable.img_default)
            .into(holder.imgDocView)
        holder.txtVerify.setOnClickListener {
            docViewListener.onDocView(userList!!.get(position),true)
        }
        holder.txtReject.setOnClickListener {
            docViewListener.onDocView(userList!!.get(position),false)
        }
        if(userList!!.get(position).isApproved || userList!!.get(position).documentStatus == "1"){
            holder.txtVerify.setText("Approved")
            holder.txtVerify.setBackgroundResource(R.drawable.button_green)
        }else{
            holder.txtVerify.setText("Verify")
            holder.txtVerify.setBackgroundResource(R.drawable.button)
        }
    }
    override fun getItemCount(): Int {
        return  userList!!.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgDocView = itemView.findViewById(R.id.imgDocView) as ImageView
        var txtDocName = itemView.findViewById(R.id.txtDocName) as TextView
        var txtVerify = itemView.findViewById(R.id.txtVerify) as TextView
        var txtReject = itemView.findViewById(R.id.txtReject) as TextView

    }
}