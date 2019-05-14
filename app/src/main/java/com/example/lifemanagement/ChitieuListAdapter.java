package com.example.lifemanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.RequiresApi;

public class ChitieuListAdapter extends ArrayAdapter<Chitieu> {
    private Context mContext;
    private int mResource ;

    ChitieuListAdapter(Context context, int resource, ArrayList<Chitieu> obj)
    {
        super(context,resource,obj);
        mContext = context;
        mResource = resource;
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String type = Objects.requireNonNull(getItem(position)).getTenchitieu();
        int value = Objects.requireNonNull(getItem(position)).getGiatri();
        String time = Objects.requireNonNull(getItem(position)).getNgayghi();
        String note = Objects.requireNonNull(getItem(position)).getGhichu();
        String loaichitieu = Objects.requireNonNull(getItem(position)).getLoaichitieu();
        String trangthai = Objects.requireNonNull(getItem(position)).getTrangthai();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);


        TextView tvTenchitieu = ((View) convertView).findViewById(R.id.tv_Tenchitieu);
        TextView tvGiatri = ((View) convertView).findViewById(R.id.tv_Giatri);
        TextView tvNgayghi = ((View)convertView).findViewById(R.id.tv_Ngayghi);
        TextView tvGhichu = ((View) convertView).findViewById(R.id.tv_Ghichu);

     /*   final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            tvlbSoluong.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_iconfinder_total_horizontal_bar_chart_comparison_4272305) );
            tvlbDaghi.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_iconfinder_edit_find_replace_118921) );
            tvlbGhichu.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_iconfinder_paperfly_701491) );

        } else {
            tvlbSoluong.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_iconfinder_total_horizontal_bar_chart_comparison_4272305));
            tvlbDaghi.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_iconfinder_edit_find_replace_118921));
            tvlbGhichu.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_iconfinder_paperfly_701491));
        }
*/
        if(loaichitieu.equals("Thu nhập")) tvGiatri.setTextColor(Color.GREEN);
        else tvGiatri.setTextColor(Color.RED);
        tvGiatri.setText(Integer.toString(value));

        tvTenchitieu.setText(type);

        tvNgayghi.setText(time);


        switch (trangthai) {
            case "Chưa trả":
                tvGhichu.setTextColor(Color.RED);
                tvGhichu.setText(note + " -> " + trangthai);
                break;
            case "Đã trả":
                tvGhichu.setTextColor(Color.GREEN);
                tvGhichu.setText(note + " -> " + trangthai);
                break;
            default:
                tvGhichu.setText(note);
                break;
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
