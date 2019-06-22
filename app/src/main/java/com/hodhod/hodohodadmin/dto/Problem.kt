package com.hodhod.hodohodadmin.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hodhod.hodohodadmin.R


enum class Problems {
    RUBBISH, CROWDING, MISSING, FATIGUE
}


data class Problem(@DrawableRes val icon: Int, @StringRes val text: Int, val type: Problems)

fun getProblems(): List<Problem> {
    return listOf(
            Problem(R.drawable.ico_1, R.string.waste_disposal, Problems.RUBBISH),
            Problem(R.drawable.ico_2, R.string.crowding_disposal, Problems.CROWDING),
            Problem(R.drawable.ico_3, R.string.missing_disposal, Problems.MISSING),
            Problem(R.drawable.ico_4, R.string.fatigue_disposal, Problems.FATIGUE)
    )
}