package com.biz.bizbulls.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.biz.bizbulls.R
import com.biz.bizbulls.model.StaffMembersResponse

class StaffMemberAdapter(var context: Context, academic_List: MutableList<StaffMembersResponse.StaffMember>) :
    RecyclerView.Adapter<StaffMemberAdapter.viewHolder>() {
    var academicList: List<StaffMembersResponse.StaffMember> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_staff_member, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val aceAcademic = academicList[position]
        holder.txtStaffName.text = aceAcademic.firstName
        var degignation = context.getString(R.string.counsellor)
        if(aceAcademic.type=="2"){
            degignation = context.getString(R.string.staff_member)
        }
        holder.txtStaffDesignation.text = degignation
    }

    override fun getItemCount(): Int {
        return academicList.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtStaffName: AppCompatTextView
        var txtStaffDesignation: AppCompatTextView


        init {
            txtStaffName = itemView.findViewById(R.id.txtStaffName)
            txtStaffDesignation = itemView.findViewById(R.id.txtStaffDesignation)
        }
    }

    init {
        academicList = academic_List
    }
}