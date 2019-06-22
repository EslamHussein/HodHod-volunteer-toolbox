package com.hodhod.hodohodadmin

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import com.hodhod.hodohodadmin.adapter.ProblemsAdapter
import com.hodhod.hodohodadmin.adapter.ServiceProvidersAdapter
import com.hodhod.hodohodadmin.dto.*
import kotlinx.android.synthetic.main.activity_maps.*


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), OnMapReadyCallback, ValueEventListener {
    private lateinit var mMap: GoogleMap

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var problemsAdapter: ProblemsAdapter

    private lateinit var database: FirebaseDatabase
    private lateinit var reportersDB: DatabaseReference
    private lateinit var reportsDB: DatabaseReference
    private var reporterList: MutableList<Reporter> = mutableListOf()

    private var issuesMarker = mutableMapOf<String, Marker>()

    private var reportersMarker = mutableMapOf<String, Marker>()


    private var issuesList = mutableListOf<Issue>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.logo)
        my_toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent))



        problemsAdapter = ProblemsAdapter(getProblems())

        problemsRecyclerView.apply {
            adapter = problemsAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 6)
        }

        viewManager = LinearLayoutManager(this)
        database = FirebaseDatabase.getInstance()
        reportersDB = database.getReference("reporters")
        reportsDB = database.getReference("reports")

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapFramgent) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap



        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        val location = LatLng(31.2213, 29.9379)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

        val zoomLevel = 16.0f //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))

        reportersDB.addValueEventListener(this)
        reportsDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(data: DataSnapshot) {
                data.children.forEach {

                    val issue = it.getValue(Issue::class.java)
                    issuesList.add(issue!!)
                    val markerOptions = MarkerOptions()
                    markerOptions.position(LatLng(issue.lat, issue.lng))
//                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.group))
                    issuesMarker[it.key.toString()] = googleMap.addMarker(markerOptions)
                    updateAnalytics()
                }
            }
        })

        reportsDB.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {


            }

            override fun onChildAdded(data: DataSnapshot, p1: String?) {

                val issue = data.getValue(Issue::class.java)

                issuesList.add(issue!!)
                val markerOptions = MarkerOptions()

                // Setting the position for the marker
                markerOptions.position(LatLng(issue.lat, issue.lng))

                issuesMarker[data.key.toString()] = googleMap.addMarker(markerOptions)

                updateAnalytics()

            }

            override fun onChildRemoved(data: DataSnapshot) {
                val issue = data.getValue(Issue::class.java)

                issuesMarker[data.key.toString()]?.remove()
                issuesMarker.remove(data.key.toString())
                issuesList.remove(issue)
                updateAnalytics()

            }
        })

    }


    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onDataChange(data: DataSnapshot) {

        data.children.forEach {

            val reporter = it.getValue(Reporter::class.java)!!

            val markerOptions = MarkerOptions()

            markerOptions.position(LatLng(reporter.lat, reporter.lng))

            markerOptions.icon(bitmapDescriptorFromVector(R.drawable.ic_health))
            reportersMarker[reporter.name] = mMap.addMarker(markerOptions)
            reporterList.add(reporter)

        }


        updateReporters(reporterList)
    }


    private fun bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(this, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    private fun updateReporters(reportersList: List<Reporter>) {
        val serviceProviderItems = reportersList.groupBy { it.speciality }.entries.map {

            ServiceProviderItem(Problems.fromString(it.key), it.value.size)
        }


        serviceProviderTotalNumberTextView.text = "Total number of volunteers ${reportersList.size} "

        val layout = LinearLayoutManager(this)
        serviceProviderRecyclerView.layoutManager = layout
        serviceProviderRecyclerView.adapter = ServiceProvidersAdapter(serviceProviderItems)


    }


    fun updateAnalytics() {
        val totalCount = issuesList.size

        val counter = issuesList.groupBy {
            it.type
        }.mapValues {
            Pair(it.value.size, it.key)
        }.toList().map {
            val count = it.second.first
            val parentage = (count.toFloat() / totalCount.toFloat()) * 100
            Problem(it.second.first, Problems.fromString(it.second.second), parentage.toInt())
        }
        problemsAdapter.updateValues(counter)

    }
}
