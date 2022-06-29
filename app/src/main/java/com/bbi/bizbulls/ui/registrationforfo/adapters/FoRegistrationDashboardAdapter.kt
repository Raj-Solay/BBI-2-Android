package com.bbi.bizbulls.ui.registrationforfo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R
import com.bbi.bizbulls.data.foregistration.steps.Data
import com.bbi.bizbulls.databinding.FoAdapterDashboardBinding
import com.bbi.bizbulls.ui.registrationforfo.interfaces.IFoRegistrationStepsClickListener
import com.bbi.bizbulls.utils.CommonUtils


/**
 * Created by Daniel.
 */
class FoRegistrationDashboardAdapter(
    private val mContext: Context,
    private val registrationsSteps: List<Data>,
    private val stepsClickListener: IFoRegistrationStepsClickListener
) : RecyclerView.Adapter<FoRegistrationDashboardAdapter.FoRegistrationDashboardViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoRegistrationDashboardViewHolder {
        val itemBinding =
            FoAdapterDashboardBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return FoRegistrationDashboardViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = registrationsSteps.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FoRegistrationDashboardViewHolder, position: Int) {
        val model = registrationsSteps[position]
        holder.itemBinding.itemName.text = model.linkName

        //val imgId = model.linkIcon.toInt()
        holder.itemBinding.itemIcon.setImageResource(model.linkIcon)

        holder.itemBinding.icnMenu.setOnClickListener {
           showPopupMenu(holder.itemBinding.icnMenu,model,position)
        }

        holder.itemBinding.itemProgress.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        holder.itemBinding.itemProgress.setBackgroundResource(com.bbi.bizbulls.R.color.status_green)

       /* Picasso.get().load(model.linkIcon).into(holder.itemBinding.itemIcon)

        if (model.profileUpdatedOn.isEmpty()) {
            holder.itemBinding.itemName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.status_failed, 0)
            holder.itemBinding.itemName.setBackgroundResource(R.color.status_red)
        } else {
            holder.itemBinding.itemName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.status_success, 0)
            holder.itemBinding.itemName.setBackgroundResource(R.color.status_green)
        }*/


        holder.itemView.setOnClickListener {
            stepsClickListener.onStepsClickListener(
                    model.linkName,
                    model.profileUpdatedOn,
                    position,
                    CommonUtils.ACTION_TYPE_VIEW
            )
        }
    }
    fun showPopupMenu(icnMenu: ImageView, model: Data, position: Int) {
        val popup = PopupMenu(mContext, icnMenu)
        //Inflating the Popup using xml file
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(com.bbi.bizbulls.R.menu.popup_menu, popup.getMenu())

        //registering popup with OnMenuItemClickListener

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener { item ->
            var actionType = CommonUtils.ACTION_TYPE_VIEW
            when (item?.itemId) {
                R.id.edit -> {
                    actionType = CommonUtils.ACTION_TYPE_EDIT
                }
                R.id.delete -> {
                    actionType = CommonUtils.ACTION_TYPE_DELETE
                }
            }
            stepsClickListener.onStepsClickListener(
                    model.linkName,
                    model.profileUpdatedOn,
                    position,actionType
            )

            true
        }

        popup.show()
    }


    /*
    * Used for binding Recyclerview
    *
    * @param itemBinding binding the Users UI elements
    * */
    class FoRegistrationDashboardViewHolder(val itemBinding: FoAdapterDashboardBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}