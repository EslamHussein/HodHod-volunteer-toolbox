package com.hodhod.hodohodadmin.dto


enum class Problems(val value: String) {
    ReportMissing("Report Missing"),
    CallForMedical("Call for Medical"),
    ReportWaterLevel("Report water level"),
    ReportCongestion("Report congestion"),
    GoToHigherGround("Go to higher ground"),
    CallForFood("Call for food");

    companion object {
        fun fromString(str: String): Problems {
            return when (str) {
                "Report Missing" -> ReportMissing
                "Call for Medical" -> CallForMedical
                "Report water level" -> ReportWaterLevel
                "Report congestion" -> ReportCongestion
                "Go to higher ground" -> GoToHigherGround
                else -> CallForFood

            }
        }
    }

}

data class Problem(val count: Int = 0, val type: Problems, val parentage: Int = 0)

fun getProblems(): List<Problem> {
    return listOf(
            Problem(type = Problems.ReportMissing),
            Problem(type = Problems.CallForMedical),
            Problem(type = Problems.ReportWaterLevel),
            Problem(type = Problems.ReportCongestion),
            Problem(type = Problems.GoToHigherGround),
            Problem(type = Problems.CallForFood)
    )
}