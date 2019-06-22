package com.hodhod.hodohodadmin.dto

import com.hodhod.hodohodadmin.R


data class ServiceProviderItem(val type: String, val number: Int) {

    fun getIcon(): Int {

        return when (type) {
            "Report Missing" -> R.drawable.food
            "Call for Medical" -> R.drawable.health
            "Report water level" -> R.drawable.ic_health
            "Report congestion" -> R.drawable.health
            "Go to higher ground" -> R.drawable.health
            else -> R.drawable.food //Call for food
        }
    }

    fun getProviderType(): String {
        return type
//        return when (type) {
//            "food" -> "مزودين الغذاء"
//            "health" -> "إسعافات أولية"
//            else -> ""
//        }

    }


    fun getProviderNumber(): String {
        return "$number Volunteer"
    }
}