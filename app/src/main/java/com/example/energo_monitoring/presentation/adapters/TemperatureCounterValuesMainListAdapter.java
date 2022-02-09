package com.example.energo_monitoring.presentation.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energo_monitoring.data.TemperatureCounterCharacteristicsParameter;
import com.example.energo_monitoring.presentation.presenters.TemperatureCounterCharacteristicsPresenter;
import com.example.energo_monitoring.R;
import com.google.android.material.textfield.TextInputLayout;

public class TemperatureCounterValuesMainListAdapter extends RecyclerView.Adapter<TemperatureCounterValuesMainListAdapter.ViewHolder> {

    TemperatureCounterCharacteristicsPresenter presenter;

    public TemperatureCounterValuesMainListAdapter(TemperatureCounterCharacteristicsPresenter presenter) {
        this.presenter = presenter;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextInputLayout parameterLayout;
        EditText parameterValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parameterLayout = itemView.findViewById(R.id.parameterLayout);
            parameterValue = itemView.findViewById(R.id.parameterName);

            parameterValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    presenter.getCurrentParameters().get(getAdapterPosition()).setValue(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        public void setData(TemperatureCounterCharacteristicsParameter parameter){
            parameterLayout.setHint(parameter.getName());
            parameterValue.setText(parameter.getValue());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_temperature_counter_characteristic, parent, false);

        return new ViewHolder(newView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(presenter.getCurrentParameters().get(position));
    }

    @Override
    public int getItemCount() {
        return presenter.getCurrentParameters().size();
    }
}
