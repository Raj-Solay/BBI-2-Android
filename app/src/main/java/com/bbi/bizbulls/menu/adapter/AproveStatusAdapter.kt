package com.foldio.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R
import com.bbi.bizbulls.menu.AproveStatusActivity
import com.bbi.bizbulls.menu.data.GetAllUserDataModel
import com.google.android.material.imageview.ShapeableImageView

class AproveStatusAdapter(
    var aproveList: ArrayList<GetAllUserDataModel>,
    var aproveArray: Array<String>,
    var aproveStatusActivity: AproveStatusActivity,
    var userassignnew: userassign
) :
    RecyclerView.Adapter<AproveStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AproveStatusAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.aprove_status_adapter, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AproveStatusAdapter.ViewHolder, position: Int) {
         holder.tv_name.text=aproveList.get(position).name
         holder.tv_email.text=aproveList.get(position).email
      var  floorOptionAdapter = ArrayAdapter(aproveStatusActivity, android.R.layout.simple_spinner_dropdown_item, aproveArray)
        holder.sp_activity.adapter = floorOptionAdapter
        holder.iv_select.setOnClickListener {
           // holder.sp_activity.visibility=View.VISIBLE
            holder.sp_activity.performClick()
        }
        holder.sp_activity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                holder.sp_activity.visibility=View.GONE
                userassignnew.selectUser(position+1)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
       /* holder.sp_activity.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View,arg2: Int, arg3: Long ) {

            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        })*/
    }

    override fun getItemCount(): Int {
        return aproveList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_profile = itemView.findViewById(R.id.iv_profile) as ShapeableImageView
        var tv_name = itemView.findViewById(R.id.tv_name) as TextView
        var tv_email = itemView.findViewById(R.id.tv_email) as TextView
        var iv_select = itemView.findViewById(R.id.iv_select) as ImageView
        var sp_activity = itemView.findViewById(R.id.sp_activity) as Spinner
    }

    public interface userassign{
        fun selectUser(int: Int)
    }
}