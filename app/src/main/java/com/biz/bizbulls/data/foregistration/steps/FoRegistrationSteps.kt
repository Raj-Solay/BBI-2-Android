package com.biz.bizbulls.data.foregistration.steps

import com.google.gson.annotations.SerializedName

data class FoRegistrationSteps(
    @SerializedName("data")
    val `data`: List<Data>
)