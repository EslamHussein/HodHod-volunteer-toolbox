package com.revolutan.hodhodclint.dto

import androidx.annotation.DrawableRes
import com.revolutan.hodhodclint.R

data class LatLng(val latitude: Double, val longitude: Double)

data class Issue(val type: String, var reporterName: String? = null, var lat: Double? = null, var lng: Double? = null)
data class Reporter(var name: String = "Reporter", var lat: Double = 0.0, var lng: Double = 0.0, val speciality: String = "")


enum class Problems(val value: String, @DrawableRes val icon: Int) {
    MedicalAssistance("Medical assistance", R.drawable.ic_health),
    FoodDistributors("Food distributors", R.drawable.ic_food),
    WaterIssues("Water Issues", R.drawable.ic_water),
    SurvivorsHandling("Survivors handling", R.drawable.ic_people),
    RoadAndBridgeFixes("Road and Bridge fixes", R.drawable.ic_road),
    CleanupOperations("Cleanup operations", R.drawable.ic_recycle);

    companion object {
        fun fromString(str: String): Problems {
            return when (str) {
                "Medical assistance" -> MedicalAssistance
                "Food distributors" -> FoodDistributors
                "Water Issues" -> WaterIssues
                "Survivors handling" -> SurvivorsHandling
                "Road and Bridge fixes" -> RoadAndBridgeFixes
                else -> CleanupOperations

            }
        }
    }

}


fun getIssuesTypes(): List<Issue> {
    return listOf(
            Issue(type = Problems.MedicalAssistance.value),
            Issue(type = Problems.FoodDistributors.value),
            Issue(type = Problems.WaterIssues.value),
            Issue(type = Problems.SurvivorsHandling.value),
            Issue(type = Problems.RoadAndBridgeFixes.value),
            Issue(type = Problems.CleanupOperations.value)
    )
}


fun generateRandomReporter(): Reporter {
    val randomLocation =
            generateRandomLocation(LatLng(31.2213, 29.9379)
                    , LatLng(31.2555, 29.9832))
    val lat = randomLocation.latitude
    val lng = randomLocation.longitude
    return listOf(Reporter("Eslam", lat, lng, Problems.MedicalAssistance.value),
            Reporter("Hussein", lat, lng, Problems.FoodDistributors.value),
            Reporter("Ahmed", lat, lng, Problems.WaterIssues.value),
            Reporter("Mohamed", lat, lng, Problems.SurvivorsHandling.value),
            Reporter("Abeer", lat, lng, Problems.RoadAndBridgeFixes.value),
            Reporter("Salah", lat, lng, Problems.CleanupOperations.value),
            Reporter("Mostafa", lat, lng, Problems.MedicalAssistance.value),
            Reporter("Yahia", lat, lng, Problems.FoodDistributors.value),
            Reporter("Magdy", lat, lng, Problems.WaterIssues.value),
            Reporter("Ibrahiem", lat, lng, Problems.SurvivorsHandling.value),
            Reporter("Hend", lat, lng, Problems.RoadAndBridgeFixes.value),
            Reporter("Dina", lat, lng, Problems.CleanupOperations.value),
            Reporter("Eman", lat, lng, Problems.MedicalAssistance.value),
            Reporter("Moamn", lat, lng, Problems.FoodDistributors.value),
            Reporter("Doaa", lat, lng, Problems.WaterIssues.value)
    ).shuffled().first()
}

fun generateRandomLocation(A: LatLng, B: LatLng): LatLng {
    val phi1 = Math.toRadians(A.latitude)
    val gamma1 = Math.toRadians(A.longitude)

    val phi2 = Math.toRadians(B.latitude)
    val deltaGamma = Math.toRadians(B.longitude - A.longitude)

    val aux1 = Math.cos(phi2) * Math.cos(deltaGamma)
    val aux2 = Math.cos(phi2) * Math.sin(deltaGamma)

    val x = Math.sqrt((Math.cos(phi1) + aux1) * (Math.cos(phi1) + aux1) + aux2 * aux2)
    val y = Math.sin(phi1) + Math.sin(phi2)
    val phi3 = Math.atan2(y, x)

    val gamma3 = gamma1 + Math.atan2(aux2, Math.cos(phi1) + aux1)

    // normalise to −180..+180°
    return LatLng(Math.toDegrees(phi3), (Math.toDegrees(gamma3) + 540) % 360 - 180)
}