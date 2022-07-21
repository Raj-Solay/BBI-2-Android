package com.bbi.bizbulls.utils

import android.os.Build
import com.bbi.bizbulls.enums.Environment
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZoneOffset.UTC
import java.util.*

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
    var REGISTRATION_FEES_DATA = "REGISTRATION_FEES_DATA"
    var REQUEST_CODE_REGISTRATION_FEE = 2001

    var REQUEST_CODE_LOCATION_SETUP = 2002
    var REQUEST_CODE_ADD_AGREEMENT = 2003
    var REQUEST_CODE_ADD_FINABILITY = 2004
    var REQUEST_CODE_SITE_VISIT = 2005
    var REQUEST_CODE_FRANCHISEE_FEE = 2006
    var REQUEST_CODE_SETUP = 2007
    var TYPE = "TYPE"
    var REQUEST_CODE_ADD_STAFF = 2008
    var IS_FRANCHISEE_FEE = "IS_FRANCHISEE_FEE"
    var REQUEST_CODE_PDF_SELECT = 2009




    const val USER_TYPE_EMPLOYEE = 2
    const val USER_TYPE_FO_TEAM = 3
    const val USER_TYPE_FM = 6
    const val USER_TYPE_FO = 1
    //const val FORMATEZTIME = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    fun dateFormat(dete:String): String?{

        var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")

        var objDate = dateFormat.parse(dete);

        var dateFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm");

        var finalDate = dateFormat2.format(objDate)
       return finalDate
    }
}
