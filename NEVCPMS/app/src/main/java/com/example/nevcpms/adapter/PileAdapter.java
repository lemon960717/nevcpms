package com.example.nevcpms.adapter;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nevcpms.Appointment;
import com.example.nevcpms.PileCaseActivity;
import com.example.nevcpms.R;
import com.example.nevcpms.bean.Pile;
import com.example.nevcpms.bean.Station;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PileAdapter extends RecyclerView.Adapter<PileAdapter.ViewHolder> {
    private List<Pile> mPileList;
    private List<Station> mStationList;//定义全局变量


    static class ViewHolder extends RecyclerView.ViewHolder {
        View pileView;
        ImageView pileImage;
        TextView pileName;
        TextView pileInfo;
        TextView pileIsUsed;
        TextView isUsed;
        TextView appointment;

        public ViewHolder(View view) {
            super(view);
            pileView = view;
            //绑定pile_item.xml布局文件
            pileImage = view.findViewById(R.id.pile_images);
            pileName = view.findViewById(R.id.pile_name);
            pileInfo = view.findViewById(R.id.pile_info);
            pileIsUsed = view.findViewById(R.id.pile_isUsed);
            isUsed = view.findViewById(R.id.isUsed);

            appointment = view.findViewById(R.id.appointment);


        }
    }

    public PileAdapter(ArrayList<Pile> pileList) {
        mPileList = pileList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pile_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Pile pile = mPileList.get(position);


                int pileId = pile.getId();

                Bundle bundle = new Bundle();
                bundle.putInt("pileId", pileId);
                Intent intent = new Intent();
                intent.setClass(v.getContext(), Appointment.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);


            }
        });


        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pile pile = mPileList.get(position);
        holder.pileName.setText("A"+pile.getId());
        holder.pileInfo.setText("充电桩功率：" + pile.getPliePower());
        holder.isUsed.setText(pile.getIsUsed() + "");

    }

    @Override
    public int getItemCount() {
        return mPileList.size();
    }

}
