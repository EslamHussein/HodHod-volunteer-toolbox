package com.revolutan.hodhodclint

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.revolutan.hodhodclint.adapter.IssuesAdapter
import com.revolutan.hodhodclint.dto.Issue
import com.revolutan.hodhodclint.dto.generateRandomReporter
import com.revolutan.hodhodclint.dto.getIssuesTypes
import com.revolutan.hodhodclint.dto.getLocation
import kotlinx.android.synthetic.main.activity_report_screen.*

class ReportScreenActivity : AppCompatActivity(), IssuesAdapter.OnItemClick {

    private val database = FirebaseDatabase.getInstance()
    private val reporter = generateRandomReporter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_screen)
        issuesRecyclerView.apply {
            adapter = IssuesAdapter(getIssuesTypes(), this@ReportScreenActivity)
            layoutManager = GridLayoutManager(this@ReportScreenActivity, 2)
        }
        val reporterRef = database.getReference("reporters")
        reporterRef.child(reporter.name).setValue(reporter)
    }

    override fun onClick(item: Issue) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("${item.type} status")

        item.apply {
            reporterName = reporter.name
            val randomLocation =
                    getLocation(31.2213, 29.9379, 20)
            lat = randomLocation.latitude
            lng = randomLocation.longitude
        }

        val reportsRef = database.getReference("reports")

        reportsRef.push().setValue(item).addOnFailureListener {
            builder.setMessage("Please try again.")
            builder.show()
        }.addOnSuccessListener {
            builder.setMessage("Issue has been submitted successfully ")
            builder.show()
        }
    }
}
