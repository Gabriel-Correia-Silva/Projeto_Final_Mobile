package com.example.projeto_final_mobile.database.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R
import com.projeto_final_mobile.database.models.FeedbackMarmita

class FeedbackAdapter(
    private val feedbacks: MutableList<FeedbackMarmita>,
    private val onEditClickListener: (FeedbackMarmita) -> Unit
) : RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    inner class FeedbackViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.findViewById(R.id.usernameTextView)
        val feedbackTextView: TextView = view.findViewById(R.id.feedbackTextView)
        val editButton: View = view.findViewById(R.id.editButton)

        fun bind(feedback: FeedbackMarmita) {
            usernameTextView.text = feedback.quardaIdUser
            feedbackTextView.text = feedback.textFeedBackMarmita
            editButton.setOnClickListener {
                onEditClickListener(feedback)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feedback, parent, false)
        return FeedbackViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.bind(feedbacks[position])
    }

    override fun getItemCount() = feedbacks.size
}