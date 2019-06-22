package com.hodhod.hodohodadmin.dto

import com.hodhod.hodohodadmin.R


data class ServiceProviderItem(val type: String, val number: Int) {

    fun getIcon(): Int {

        return when (type) {
            "food" -> R.drawable.food
            "health" -> R.drawable.health
            else -> R.drawable.food
        }
    }

    fun getProviderType(): String {
        return when (type) {
            "food" -> "مزودين الغذاء"
            "health" -> "إسعافات أولية"
            else -> ""
        }

    }


    fun getProviderNumber(): String {
        return " عدد$number متطوع "
    }
}