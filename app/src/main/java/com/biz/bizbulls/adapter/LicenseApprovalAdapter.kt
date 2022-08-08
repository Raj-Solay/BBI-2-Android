package com.biz.bizbulls.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.biz.bizbulls.DocViewListener
import com.biz.bizbulls.KycDocViewActivity
import com.biz.bizbulls.R
import com.biz.bizbulls.model.AgreementsApprovalRes
import com.biz.bizbulls.model.LicenseApprovalRes
import com.biz.bizbulls.ui.FullImageViewDialog
import com.squareup.picasso.Picasso

class LicenseApprovalAdapter(
    var kycDocViewActivity: KycDocViewActivity,
    var userList: List<LicenseApprovalRes.Data>?,
    var docViewListener: DocViewListener,
    var approval_type: Int
) :
    RecyclerView.Adapter<LicenseApprovalAdapter.ViewHolder>() {
    private var context: Context? = null;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.doc_approval_adapter,
                parent, false)
        context = parent.context;
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(!userList!!.get(position).isApproved){
            holder.txtDocName.text = "License"
            Picasso.get().load(userList!!.get(position).photo)
                .placeholder(R.drawable.img_default)
                .into(holder.imgDocView)
            holder.txtVerify.setOnClickListener {
                docViewListener.onLicenseView(userList!!.get(position),true)
            }
            holder.txtReject.setOnClickListener {
                docViewListener.onLicenseView(userList!!.get(position),false)
            }
            holder.txtVerify
            holder.imgDocView.setOnClickListener {
                showFullDialog(userList!!.get(position).photo)
            }
        }

      /*  if(userList!!.get(position).isApproved *//*|| userListLocation!!.get(position).documentStatus == "1"*//*){
            holder.txtVerify.setText("Approved")
            holder.txtVerify.setBackgroundResource(R.drawable.button_green)
        }else{
            holder.txtVerify.setText("Verify")
            holder.txtVerify.setBackgroundResource(R.drawable.button)
        }*/

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
    fun showFullDialog( fileName: String?) {
        val showDialog = FullImageViewDialog(kycDocViewActivity!!)
        showDialog.show()
        Picasso.get().load(fileName)
            .placeholder(R.drawable.img_default)
            .into(showDialog.imgFull)
    }
}