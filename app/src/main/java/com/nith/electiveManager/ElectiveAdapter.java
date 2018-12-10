package com.nith.electiveManager;

import android.content.Context;
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

    private int priorities = 0;
    private ArrayList<Elective> electives;
    private Context context;
    ElectiveAdapter(ArrayList<Elective> electives, Context context) {
        this.electives = electives;
        this.context = context;
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
    public void onBindViewHolder(@NonNull final ElectiveViewHolder holder, final int position) {
        holder.checkableChipView.setText(electives.get(position).getElective());
        holder.checkableChipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkableChipView.isChecked()) {
                    electives.get(position).setSelected(true);
                    priorities++;
                    Toast.makeText(context, "Priority: "+priorities, Toast.LENGTH_SHORT).show();
                }
                else {
                    electives.get(position).setSelected(false);
                    priorities--;
                    Toast.makeText(context, "Priority: "+priorities, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return electives.size();
    }

    public ArrayList<Elective> getElectives() {
        return electives;
    }
}
