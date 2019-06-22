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

data class Problem(val count: Int, val type: Problems)

fun getProblems(): List<Problem> {
    return listOf(
            Problem(0, Problems.ReportMissing),
            Problem(0, Problems.CallForMedical),
            Problem(0, Problems.ReportWaterLevel),
            Problem(0, Problems.ReportCongestion),
            Problem(0, Problems.GoToHigherGround),
            Problem(0, Problems.CallForFood)
    )
}