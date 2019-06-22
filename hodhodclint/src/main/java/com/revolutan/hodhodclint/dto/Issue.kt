package com.revolutan.hodhodclint.dto

import androidx.annotation.DrawableRes
import com.revolutan.hodhodclint.R

data class LatLng(val latitude: Double, val longitude: Double)

data class Issue(val type: String, @DrawableRes val icon: Int, var reporterName: String? = null, var lat: Double? = null, var lng: Double? = null)


fun getIssuesTypes(): List<Issue> {
    return listOf(
            Issue("Report Missing", R.drawable.ic_launcher_foreground),
            Issue("Call for Medical", R.drawable.ic_launcher_foreground),
            Issue("Report water level", R.drawable.ic_launcher_foreground),
            Issue("Report congestion", R.drawable.ic_launcher_foreground),
            Issue("Go to higher ground", R.drawable.ic_launcher_foreground),
            Issue("Call for food", R.drawable.ic_launcher_foreground)
    )
}


fun generateRandomNames(): String {
    return listOf("Eslam", "Hussein", "Ahmed", "Mohamed", "Abeer", "Salah", "Mostafa", "Yahia", "Magdy").shuffled().first()
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