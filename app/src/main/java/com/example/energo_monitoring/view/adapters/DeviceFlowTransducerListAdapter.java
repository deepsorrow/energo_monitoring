package com.example.energo_monitoring.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energo_monitoring.model.DeviceFlowTransducer;
import com.example.energo_monitoring.presenter.CheckLengthOfStraightLinesPresenter;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.databinding.ListItemDeviceFlowTransducerCheckLengthBinding;
import com.example.energo_monitoring.view.viewmodel.CheckLengthViewModel;

public class DeviceFlowTransducerListAdapter extends RecyclerView.Adapter<DeviceFlowTransducerListAdapter.ViewHolder> {

    boolean isItFirst = true;
    CheckLengthOfStraightLinesPresenter presenter;

    public DeviceFlowTransducerListAdapter(CheckLengthOfStraightLinesPresenter presenter) {
        this.presenter = presenter;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ListItemDeviceFlowTransducerCheckLengthBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListItemDeviceFlowTransducerCheckLengthBinding.bind(itemView);

            Observer<Integer> currentId = integer -> {
                binding.card.setDragged(integer.equals(getAdapterPosition()));
            };
            presenter.getModel().getCurrentDeviceId().observeForever(currentId);

            binding.card.setOnClickListener(v -> {
                presenter.onDeviceCardClicked(getAdapterPosition());
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_device_flow_transducer_check_length, parent, false);

        return new ViewHolder(newView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setResult(presenter.getResult(holder.getAdapterPosition()));
        if(isItFirst) {
            holder.binding.card.setDragged(true);
            isItFirst = false;
        }
    }

    @Override
    public int getItemCount() {
        return presenter.devices.size();
    }
}
