package com.bbi.bizbulls.utils

import com.bbi.bizbulls.enums.Environment

/**
 * Singleton container for application constants and globals
 */
object Globals {

    // Base URLs by Environment
        private const val BASE_URL_PROD = "https://bizbulls.beurboss.com/"
    private const val BASE_URL_QA = "https://bizbulls.beurboss.com/"
    private const val BASE_URL_DEV = "https://bizbulls.beurboss.com/"

     const val ASSET_URL = "api/assets/"

    val BASE_URL = when (CommonUtils.environment) {
        Environment.PRODUCTION -> BASE_URL_PROD
        Environment.QA -> BASE_URL_QA
        Environment.DEVELOPMENT -> BASE_URL_DEV
    }

    const val APPLICATION_NAME = "Biz Bulls"
    var See_All_Type = 2
    var Item_Type = 1
    var SEE_ALL_TAG = "See All>>"

    const val USER_TYPE_EMPLOYEE = 1
    const val USER_TYPE_FO = 2

}
