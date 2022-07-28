package com.biz.bizbulls.ui.registrationforfo.interfaces

import com.biz.bizbulls.data.foregistration.steps.Data

interface IFoRegistrationStepsClickListener {
    fun onStepsClickListener(model : Data, position: Int, actionType: Int)
}