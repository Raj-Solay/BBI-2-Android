package com.bbi.bizbulls.ui.registrationforfo.interfaces

import com.bbi.bizbulls.data.foregistration.steps.Data

interface IFoRegistrationStepsClickListener {
    fun onStepsClickListener(model : Data, position: Int, actionType: Int)
}