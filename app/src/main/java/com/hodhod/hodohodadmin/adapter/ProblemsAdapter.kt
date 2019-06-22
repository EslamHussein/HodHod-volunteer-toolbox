package com.hodhod.hodohodadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hodhod.hodohodadmin.R
import com.hodhod.hodohodadmin.dto.Problem


class ProblemsAdapter(private val items: List<Problem> = emptyList(), var values: Map<String, Int> = emptyMap()) : RecyclerView.Adapter<ProblemsAdapter.ViewHolder>() {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.problem_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.problemImageView.setImageResource(item.icon)
        holder.problemNumberTextView.text = (values[item.type.name.toLowerCase()]
                ?: 0).toString()
        holder.titleTextView.text = context.getString(item.text)
    }

    fun updateValues(counter: Map<String, Int>) {
        values = counter
        notifyDataSetChanged()

    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var problemImageView: ImageView = v.findViewById(R.id.problemImageView)
        var problemNumberTextView: TextView = v.findViewById(R.id.problemNumberTextView)
        var titleTextView: TextView = v.findViewById(R.id.titleTextView)
    }
}