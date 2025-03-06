package com.example.nevcpms.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nevcpms.Appointment;
import com.example.nevcpms.MyDataBaseHelper;
import com.example.nevcpms.R;
import com.example.nevcpms.bean.MyAppointData;
import com.example.nevcpms.bean.Pile;
import com.example.nevcpms.util.CommonData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PileCancelAdapter extends RecyclerView.Adapter<PileCancelAdapter.ViewHolder> {

    private List<MyAppointData> myAppointDataList;

    private List<String> mData;
    private Context mContext;


    public PileCancelAdapter(Context context, List<MyAppointData> myAppointDataList) {
        this.mContext = context;
        this.myAppointDataList = myAppointDataList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View pileView;
        ImageView pileImage;
        TextView pileName;
        TextView pileInfo;
        TextView pileIsUsed;
        TextView isUsed;
        TextView appointment_cancel;
        TextView isUsed_cancel;
        public ViewHolder(View view) {
            super(view);
            pileView = view;

            //绑定pile_item.xml布局文件
            pileImage = view.findViewById(R.id.pile_images);
            pileName = view.findViewById(R.id.pile_name);
            pileInfo = view.findViewById(R.id.pile_info);
            pileIsUsed = view.findViewById(R.id.pile_isUsed);

            isUsed_cancel = view.findViewById(R.id.isUsed_cancel);
            appointment_cancel = view.findViewById(R.id.appointment_cancel);


        }
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pileappointment_cancel, parent, false);
        final PileCancelAdapter.ViewHolder holder = new PileCancelAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {


        holder.appointment_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                MyAppointData myAppointData = myAppointDataList.get(position);


                MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(mContext, "LBS.db", null, 1);

                int pileId = myAppointData.getPile().getId();
                String phoneNumber = CommonData.getInstance().getPhoneNum();

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提示");
                builder.setMessage("确定取消预约吗？");

                builder.setPositiveButton("确定", (dialog, which) -> {
                    // 用户点击确定按钮后的操作
                    myDataBaseHelper.deleteAppointment(phoneNumber, pileId);
                    myDataBaseHelper.updatePile(pileId, 0);

                    myAppointDataList.remove(position);
                    notifyDataSetChanged();
                });
                builder.setNegativeButton("取消", (dialog, which) -> {
                    // 用户点击取消按钮后的操作
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        MyAppointData myAppointData = myAppointDataList.get(position);
        holder.pileName.setText(myAppointData.getPile().getId() + "");
        holder.pileInfo.setText("充电桩功率：" + myAppointData.getPile().getPliePower());
        holder.isUsed_cancel.setText("预约的时间为："+myAppointData.getStarTime()+"—"+myAppointData.getEndTime());
    }

    @Override
    public int getItemCount() {
        return myAppointDataList.size();
    }

}
