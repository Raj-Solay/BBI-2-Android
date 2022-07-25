package com.foldio.android.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.*
import com.bbi.bizbulls.model.LocationApprovalRes
import com.bbi.bizbulls.ui.FullImageViewDialog
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso

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

      holder.txtDocName.text = "Location : "+userListLocation!!.get(position).locationName

        Picasso.get().load(userListLocation!!.get(position).spaceBluePrintFile)
            .placeholder(R.drawable.img_default)
            .into(holder.imgOfficeMeasurement)
        Picasso.get().load(userListLocation!!.get(position).videos)
            .placeholder(R.drawable.img_default)
            .into(holder.imgVideo)

        holder.imgOfficeMeasurement.setOnClickListener {
            showFullDialog(userListLocation!!.get(position).spaceBluePrintFile)
        }
        holder.imgVideo.setOnClickListener {
            showFullDialog(userListLocation!!.get(position).videos)
        }

        var imgList = userListLocation!!.get(position).images!!.split(",")
        if(imgList.isNotEmpty()) {
            Picasso.get().load(imgList[0])
                .placeholder(R.drawable.img_default)
                .into(holder.imgOne)
            holder.imgOne.setOnClickListener {
                showFullDialog(imgList[0])
            }
        }
        if(imgList.size > 1){
            holder.uploadImageTwo.visibility = View.VISIBLE
            Picasso.get().load(imgList[1])
                .placeholder(R.drawable.img_default)
                .into(holder.imgTwo)
            holder.imgTwo.setOnClickListener {
                showFullDialog(imgList[1])
            }
        }
        if(imgList.size > 2){
            holder.uploadImageThree.visibility = View.VISIBLE
            Picasso.get().load(imgList[1])
                .placeholder(R.drawable.img_default)
                .into(holder.imgThree)
            holder.imgThree.setOnClickListener {
                showFullDialog(imgList[2])
            }
        }
        if(imgList.size > 3){
            holder.uploadImageFour.visibility = View.VISIBLE
            Picasso.get().load(imgList[1])
                .placeholder(R.drawable.img_default)
                .into(holder.imgFour)
            holder.imgFour.setOnClickListener {
                showFullDialog(imgList[3])
            }
        }
        if(imgList.size > 4){
            holder.uploadImageFive.visibility = View.VISIBLE
            Picasso.get().load(imgList[1])
                .placeholder(R.drawable.img_default)
                .into(holder.imgFive)
            holder.imgFive.setOnClickListener {
                showFullDialog(imgList[4])
            }
        }
        if(imgList.size == 5){
            holder.uploadImageOne.visibility = View.VISIBLE
            holder.uploadImageTwo.visibility = View.VISIBLE
            holder.uploadImageThree.visibility = View.VISIBLE
            holder.uploadImageFour.visibility = View.VISIBLE
            holder.uploadImageFive.visibility = View.VISIBLE
        }else if(imgList.size == 4){
            holder.uploadImageOne.visibility = View.VISIBLE
            holder.uploadImageTwo.visibility = View.VISIBLE
            holder.uploadImageThree.visibility = View.VISIBLE
            holder.uploadImageFour.visibility = View.VISIBLE
            holder.uploadImageFive.visibility = View.GONE
        } else if(imgList.size == 3){
            holder.uploadImageOne.visibility = View.VISIBLE
            holder.uploadImageTwo.visibility = View.VISIBLE
            holder.uploadImageThree.visibility = View.VISIBLE
            holder.uploadImageFour.visibility = View.GONE
            holder.uploadImageFive.visibility = View.GONE
        }else if(imgList.size == 4){
            holder.uploadImageOne.visibility = View.VISIBLE
            holder.uploadImageTwo.visibility = View.VISIBLE
            holder.uploadImageThree.visibility = View.GONE
            holder.uploadImageFour.visibility = View.GONE
            holder.uploadImageFive.visibility = View.GONE
        }else if(imgList.size == 5){
            holder.uploadImageOne.visibility = View.VISIBLE
            holder.uploadImageTwo.visibility = View.GONE
            holder.uploadImageThree.visibility = View.GONE
            holder.uploadImageFour.visibility = View.GONE
            holder.uploadImageFive.visibility = View.GONE
        }



        holder.txtVerify.setOnClickListener {
            docViewListener.onDocLocationView(userListLocation!!.get(position))
        }
        if(userListLocation!!.get(position).isApproved /*|| userListLocation!!.get(position).documentStatus == "1"*/){
            holder.txtVerify.setText("Approved")
            holder.txtVerify.setBackgroundResource(R.drawable.button_green)
        }else{
            holder.txtVerify.setText("Verify")
            holder.txtVerify.setBackgroundResource(R.drawable.button)
        }
        holder.imgMap.setOnClickListener {
            showMapDialog(userListLocation!!.get(position))
        }
    }
    override fun getItemCount(): Int {
        return  userListLocation!!.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgVideo = itemView.findViewById(R.id.imgVideo) as ImageView
        var imgOfficeMeasurement = itemView.findViewById(R.id.imgOfficeMeasurement) as ImageView
        var imgMap = itemView.findViewById(R.id.imgMap) as ImageView
        var txtDocName = itemView.findViewById(R.id.txtDocName) as TextView
        var txtVerify = itemView.findViewById(R.id.txtVerify) as TextView
        var uploadImageOne = itemView.findViewById(R.id.uploadImageOne) as LinearLayout
        var uploadImageTwo = itemView.findViewById(R.id.uploadImageTwo) as LinearLayout
        var uploadImageThree = itemView.findViewById(R.id.uploadImageThree) as LinearLayout
        var uploadImageFour = itemView.findViewById(R.id.uploadImageFour) as LinearLayout
        var uploadImageFive = itemView.findViewById(R.id.uploadImageFive) as LinearLayout
        var imgOne = itemView.findViewById(R.id.imgpancard) as ImageView
        var imgTwo = itemView.findViewById(R.id.imgpancard2) as ImageView
        var imgThree = itemView.findViewById(R.id.imgpancard3) as ImageView
        var imgFour = itemView.findViewById(R.id.imgpancard4) as ImageView
        var imgFive = itemView.findViewById(R.id.imgpancard5) as ImageView
       // var txtApproved = itemView.findViewById(R.id.txtApproved) as TextView

    }

    fun showMapDialog(get: LocationApprovalRes.Data) {

        val intent = Intent(context, LocationDocViewActivity::class.java)
        intent.putExtra("LAT",""+get.latitute)
        intent.putExtra("LOG",""+get.longitude)
        intent.putExtra("ADDRESS",""+get.locationName)
        context!!.startActivity(intent)
    }

    fun showFullDialog( fileName: String?) {
        val showDialog = FullImageViewDialog(kycDocViewActivity!!)
        showDialog.show()
        Picasso.get().load(fileName)
            .placeholder(R.drawable.img_default)
            .into(showDialog.imgFull)
    }
}