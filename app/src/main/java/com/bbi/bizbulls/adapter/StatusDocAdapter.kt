package com.foldio.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.KycStatusViewActivity
import com.bbi.bizbulls.R
import com.bbi.bizbulls.model.StatusDataRes
import com.squareup.picasso.Picasso

class StatusDocAdapter(var userList: List<StatusDataRes.Doc>?, var docViewListener: KycStatusViewActivity) :
    RecyclerView.Adapter<StatusDocAdapter.ViewHolder>() {
    private var context: Context? = null;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusDocAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.doc_status_adapter,
                parent, false)
        context = parent.context;
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: StatusDocAdapter.ViewHolder, position: Int) {
     holder.txtDocName.text="Document Id or Name : "+userList!!.get(position).documentId
        Picasso.get().load(userList!!.get(position).documentName)
            .placeholder(R.drawable.img_default)
            .into(holder.imgDocView)

        holder.imgDocView.setOnClickListener {
            docViewListener.showDocDialog(userList!!.get(position).documentName)
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