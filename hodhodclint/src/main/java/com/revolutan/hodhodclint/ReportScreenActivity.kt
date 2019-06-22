package com.revolutan.hodhodclint

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.revolutan.hodhodclint.adapter.IssuesAdapter
import com.revolutan.hodhodclint.dto.*
import kotlinx.android.synthetic.main.activity_report_screen.*

class ReportScreenActivity : AppCompatActivity(), IssuesAdapter.OnItemClick {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_screen)
        issuesRecyclerView.apply {
            adapter = IssuesAdapter(getIssuesTypes(), this@ReportScreenActivity)
            layoutManager = GridLayoutManager(this@ReportScreenActivity, 2)
        }
    }

    override fun onClick(item: Issue) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("${item.type} status")

        item.apply {
            reporterName = generateRandomNames()
            val randomLocation =
                    generateRandomLocation(LatLng(0.205610, 32.583221), LatLng(0.188618, 32.595352))
            lat = randomLocation.latitude
            lng = randomLocation.longitude

        }

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("reports")

        myRef.push().setValue(item).addOnFailureListener {
            builder.setMessage("Please try again.")
            builder.show()

        }.addOnSuccessListener {
            builder.setMessage("Issue has been submitted successfully ")
            builder.show()

        }


    }
}
