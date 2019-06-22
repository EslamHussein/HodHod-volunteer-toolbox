package com.hodhod.hodohodadmin.dto

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


class SampleClusterItem(var reporter: Reporter) : ClusterItem {
    override fun getPosition(): LatLng = reporter.getLocation()

    override fun getSnippet(): String = ""

    override fun getTitle(): String = reporter.name


}
