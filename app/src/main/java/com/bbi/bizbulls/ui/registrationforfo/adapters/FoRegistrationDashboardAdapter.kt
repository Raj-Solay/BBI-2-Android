package com.bbi.bizbulls.ui.registrationforfo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bbi.bizbulls.R
import com.bbi.bizbulls.data.foregistration.steps.Data
import com.bbi.bizbulls.databinding.FoAdapterDashboardBinding
import com.bbi.bizbulls.ui.registrationforfo.interfaces.IFoRegistrationStepsClickListener
import com.squareup.picasso.Picasso

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
        Picasso.get().load(model.linkIcon).into(holder.itemBinding.itemIcon)

        if (model.profileUpdatedOn.isEmpty()) {
            holder.itemBinding.itemName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.status_failed, 0)
            holder.itemBinding.itemName.setBackgroundResource(R.color.status_red)
        } else {
            holder.itemBinding.itemName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.status_success, 0)
            holder.itemBinding.itemName.setBackgroundResource(R.color.status_green)
        }


        holder.itemView.setOnClickListener {
            stepsClickListener.onStepsClickListener(
                model.linkName,
                model.profileUpdatedOn,
                position
            )
        }
    }


    /*
    * Used for binding Recyclerview
    *
    * @param itemBinding binding the Users UI elements
    * */
    class FoRegistrationDashboardViewHolder(val itemBinding: FoAdapterDashboardBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}