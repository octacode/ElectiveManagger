package com.nith.electiveManager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.okdroid.checkablechipview.CheckableChipView;

import java.util.ArrayList;

public class ElectiveAdapter extends RecyclerView.Adapter<ElectiveAdapter.ElectiveViewHolder> {

    private ArrayList<Elective> electives;
    ElectiveAdapter(ArrayList<Elective> electives) {
        this.electives = electives;
    }

    class ElectiveViewHolder extends RecyclerView.ViewHolder {
        CheckableChipView checkableChipView;
        ElectiveViewHolder(View itemView) {
            super(itemView);
            checkableChipView = itemView.findViewById(R.id.chip);
        }
    }

    @NonNull
    @Override
    public ElectiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ElectiveViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_elective_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ElectiveViewHolder holder, int position) {
        holder.checkableChipView.setText(electives.get(position).getElective());
        if (holder.checkableChipView.isChecked())
            electives.get(position).setSelected(true);
    }

    @Override
    public int getItemCount() {
        return electives.size();
    }

    public ArrayList<Elective> getElectives() {
        return electives;
    }
}
