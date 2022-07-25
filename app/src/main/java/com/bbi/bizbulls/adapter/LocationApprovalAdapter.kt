package com.foldio.android.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.*
import com.bbi.bizbulls.model.LocationApprovalRes
import com.bbi.bizbulls.ui.MapApprovalDialog
import com.google.android.gms.maps.model.LatLng

class LocationApprovalAdapter(
    var kycDocViewActivity: KycDocViewActivity,
    var userListLocation: List<LocationApprovalRes.Data>?,
    var docViewListener: DocViewListener,
    var approval_type: Int
) :
    RecyclerView.Adapter<LocationApprovalAdapter.ViewHolder>() {
    private var context: Context? = null;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationApprovalAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.location_approval_adapter,
                parent, false)
        context = parent.context;
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: LocationApprovalAdapter.ViewHolder, position: Int) {
        var documentName  =""

      holder.txtDocName.text = ""+userListLocation!!.get(position).locationName
       /* Picasso.get().load(userListLocation!!.get(position).locationName)
            .placeholder(R.drawable.img_default)
            .into(holder.imgDocView)*/
        holder.txtVerify.setOnClickListener {
           // docViewListener.onDocView(userListLocation!!.get(position))
        }
      /*  if(userListLocation!!.get(position).isApproved || userListLocation!!.get(position).documentStatus == "1"){
            holder.txtVerify.setText("Approved")
            holder.txtVerify.setBackgroundResource(R.drawable.button_green)
        }else{
            holder.txtVerify.setText("Verify")
            holder.txtVerify.setBackgroundResource(R.drawable.button)
        }*/
        holder.imgMap.setOnClickListener {
            showMapDialog(userListLocation!!.get(position))
        }
    }
    override fun getItemCount(): Int {
        return  userListLocation!!.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgMap = itemView.findViewById(R.id.imgMap) as ImageView
        var txtDocName = itemView.findViewById(R.id.txtDocName) as TextView
        var txtVerify = itemView.findViewById(R.id.txtVerify) as TextView
       // var txtApproved = itemView.findViewById(R.id.txtApproved) as TextView

    }

    fun showMapDialog(get: LocationApprovalRes.Data) {

        val intent = Intent(context, LocationDocViewActivity::class.java)
        intent.putExtra("LAT",""+get.latitute)
        intent.putExtra("LOG",""+get.longitude)
        intent.putExtra("ADDRESS",""+get.locationName)
        context!!.startActivity(intent)
    }
}