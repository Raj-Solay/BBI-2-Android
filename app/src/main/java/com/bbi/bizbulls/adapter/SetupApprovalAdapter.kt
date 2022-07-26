package com.bbi.bizbulls.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.DocViewListener
import com.bbi.bizbulls.KycDocViewActivity
import com.bbi.bizbulls.R
import com.bbi.bizbulls.model.AgreementsApprovalRes
import com.bbi.bizbulls.model.ApprovalDocRes
import com.bbi.bizbulls.model.StaffApprovalRes
import com.bbi.bizbulls.ui.FullImageViewDialog
import com.squareup.picasso.Picasso

class SetupApprovalAdapter(
    var kycDocViewActivity: KycDocViewActivity,
    var userList: List<StaffApprovalRes.Data>?,
    var docViewListener: DocViewListener,
    var approval_type: Int
) :
    RecyclerView.Adapter<SetupApprovalAdapter.ViewHolder>() {
    private var context: Context? = null;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.setup_approval_adapter,
                parent, false)
        context = parent.context;
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

      holder.txtFirstName.text = ""+userList!!.get(position).firstName
      holder.txtLastName.text = ""+userList!!.get(position).lastName
        var degignation = context!!.getString(R.string.counsellor)
        if(userList!!.get(position).type=="2"){
            degignation = context!!.getString(R.string.staff_member)
        }
        holder.txtType.text = ""+degignation
        holder.txtMobile.text = "Mobile No : "+userList!!.get(position).mobile
      /*  if(userList!!.get(position).isApproved *//*|| userListLocation!!.get(position).documentStatus == "1"*//*){
            holder.txtVerify.setText("Approved")
            holder.txtVerify.setBackgroundResource(R.drawable.button_green)
        }else{
            holder.txtVerify.setText("Verify")
            holder.txtVerify.setBackgroundResource(R.drawable.button)
        }*/

        Picasso.get().load(userList!!.get(position).photo)
            .placeholder(R.drawable.img_default)
            .into(holder.imgPhoto)
        Picasso.get().load(userList!!.get(position).resume)
            .placeholder(R.drawable.img_default)
            .into(holder.imgResume)
        holder.txtVerify.setOnClickListener {
            docViewListener.onDocSetupView(userList!!.get(position))
        }

        holder.imgPhoto.setOnClickListener {
            showFullDialog(userList!!.get(position).photo)
        }
        holder.imgResume.setOnClickListener {
            showFullDialog(userList!!.get(position).resume)
        }

    }
    override fun getItemCount(): Int {
        return  userList!!.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto = itemView.findViewById(R.id.imgPhoto) as ImageView
        var imgResume = itemView.findViewById(R.id.imgResume) as ImageView
        var txtFirstName = itemView.findViewById(R.id.txtFirstName) as TextView
        var txtLastName = itemView.findViewById(R.id.txtLastName) as TextView
        var txtType = itemView.findViewById(R.id.txtType) as TextView
        var txtMobile = itemView.findViewById(R.id.txtMobile) as TextView
        var txtVerify = itemView.findViewById(R.id.txtVerify) as TextView
       // var txtApproved = itemView.findViewById(R.id.txtApproved) as TextView

    }
    fun showFullDialog( fileName: String?) {
        val showDialog = FullImageViewDialog(kycDocViewActivity!!)
        showDialog.show()
        Picasso.get().load(fileName)
            .placeholder(R.drawable.img_default)
            .into(showDialog.imgFull)
    }

}