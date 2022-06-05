package com.example.energy_monitoring.checks.ui.adapters

import com.example.energy_monitoring.checks.data.TempParameter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import android.widget.EditText
import com.example.energy_monitoring.R
import android.text.Editable
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.energy_monitoring.checks.ui.utils.AfterTextChangedListener

class MetricsListAdapter(
    private val parameters: List<TempParameter>,
    private val saveParameters: (List<TempParameter>) -> Unit
) : RecyclerView.Adapter<MetricsListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parameterLayout: TextInputLayout
        var parameterValue: EditText
        var unitSystemHint: TextView

        init {
            parameterLayout = itemView.findViewById(R.id.parameterLayout)
            unitSystemHint = itemView.findViewById(R.id.unitSystemHint)
            parameterValue = itemView.findViewById(R.id.parameterName)
            parameterValue.addTextChangedListener(object : AfterTextChangedListener {
                override fun afterTextChanged(s: Editable) {
                    parameters[adapterPosition].value = s.toString()
                    saveParameters(parameters)
                }
            })
        }

        fun setData(parameter: TempParameter) {
            parameterLayout.hint = parameter.name
            unitSystemHint.text = parameter.unitSystem
            parameterValue.setText(parameter.value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val newView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_metric, parent, false)
        return ViewHolder(newView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(parameters[position])
    }

    override fun getItemCount(): Int {
        return parameters.size
    }
}