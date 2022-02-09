package com.example.energo_monitoring.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energo_monitoring.data.devices.DeviceCounter;
import com.example.energo_monitoring.data.TemperatureCounterCharacteristicsParameter;
import com.example.energo_monitoring.presentation.presenters.TemperatureCounterCharacteristicsPresenter;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.databinding.ListItemTemperatureCounterCharacteristicsTabBinding;

public class TemperatureCounterValuesTabsAdapter extends RecyclerView.Adapter<TemperatureCounterValuesTabsAdapter.TabsViewHolder> {

    boolean itIsFirst = true;
    TemperatureCounterCharacteristicsPresenter presenter;

    public TemperatureCounterValuesTabsAdapter(TemperatureCounterCharacteristicsPresenter presenter) {
        this.presenter = presenter;
    }

    class TabsViewHolder extends RecyclerView.ViewHolder{
        public ListItemTemperatureCounterCharacteristicsTabBinding binding;
        public TabsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListItemTemperatureCounterCharacteristicsTabBinding.bind(itemView);

            Observer<Integer> currentId = integer -> {
                if(binding.card.isDragged()){
                    boolean complete = true;
                    boolean touched  = false;
                    for(TemperatureCounterCharacteristicsParameter parameter : presenter.getCurrentParameters()){
                        if(parameter.getValue() == null || parameter.getValue().equals(""))
                            complete = false;
                        else
                            touched = true;
                    }

                    if(complete)
                        binding.tabName.setCompoundDrawablesWithIntrinsicBounds(
                                0,R.drawable.ic_baseline_check_circle_24, 0, 0);
                    else if(touched)
                        binding.tabName.setCompoundDrawablesWithIntrinsicBounds(
                                0,R.drawable.ic_baseline_incomplete_circle_24, 0, 0);
                    else
                        binding.tabName.setCompoundDrawablesWithIntrinsicBounds(
                                0,R.drawable.ic_baseline_trip_origin_24, 0, 0);
                }

                binding.card.setDragged(integer.equals(getAdapterPosition()));
            };
            presenter.getModel().getCurrentDeviceId().observeForever(currentId);

            itemView.setOnClickListener(v -> {
                presenter.getModel().getCurrentDeviceId().setValue(getAdapterPosition());
                presenter.setCurrentDevice(getAdapterPosition());
                presenter.getAdapterParameters().notifyDataSetChanged();
            });
        }

        public void setData(DeviceCounter device){
            binding.tabName.setText(device.getDeviceName() + " №" + device.getDeviceNumber());
        }
    }

    @NonNull
    @Override
    public TabsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_temperature_counter_characteristics_tab, parent, false);

        TabsViewHolder holder = new TabsViewHolder(newView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TabsViewHolder holder, int position) {
        holder.setData(presenter.getDevices().get(position));
        if(itIsFirst) {
            holder.binding.card.setDragged(true);
            itIsFirst = false;
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getDevices().size();
    }
}
