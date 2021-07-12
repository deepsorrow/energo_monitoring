package com.example.energo_monitoring.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.databinding.ListItemDeviceInspectionTabBinding;
import com.example.energo_monitoring.model.TemperatureCounterCharacteristicsParameter;
import com.example.energo_monitoring.presenter.DeviceInspectionPresenter;

public class DeviceInspectionTabsAdapter extends RecyclerView.Adapter<DeviceInspectionTabsAdapter.ViewHolder> {

    boolean isItFirst = true;
    DeviceInspectionPresenter presenter;

    public DeviceInspectionTabsAdapter(DeviceInspectionPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_device_inspection_tab, parent, false);

        ViewHolder holder = new ViewHolder(newView);
        if(holder.getAdapterPosition() == 0)
            holder.binding.card.setDragged(true);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
        if(isItFirst) {
            holder.binding.card.setDragged(true);
            isItFirst = false;
        }
    }

    @Override
    public int getItemCount() {
        return presenter.devices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ListItemDeviceInspectionTabBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListItemDeviceInspectionTabBinding.bind(itemView);
            binding.card.setOnClickListener(v -> {
                presenter.model.getCurrentDeviceId().setValue(getAdapterPosition());
                presenter.replaceCurrentFragment();
            });

            Observer<Integer> currentId = integer -> {
                if(binding.card.isDragged()){
//                    int filledState = presenter.devices.get(getAdapterPosition()).getFilledState();
//                    if(filledState == 2)
//                        binding.tabName.setCompoundDrawablesWithIntrinsicBounds(
//                                0,R.drawable.ic_baseline_check_circle_24, 0, 0);
//                    else if(filledState == 1)
//                        binding.tabName.setCompoundDrawablesWithIntrinsicBounds(
//                                0,R.drawable.ic_baseline_incomplete_circle_24, 0, 0);
//                    else
//                        binding.tabName.setCompoundDrawablesWithIntrinsicBounds(
//                                0,R.drawable.ic_baseline_trip_origin_24, 0, 0);
                }

                binding.card.setDragged(integer.equals(getAdapterPosition()));
            };
            presenter.model.getCurrentDeviceId().observeForever(currentId);
        }

        public void setData(int position){
            binding.setDevice(presenter.devices.get(position));
        }
    }
}
