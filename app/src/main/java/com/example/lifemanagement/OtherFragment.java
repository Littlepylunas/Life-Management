package com.example.lifemanagement;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import androidx.annotation.RequiresApi;

public class OtherFragment extends Fragment {
    private final int MENU_EDIT = 1;
    SQLiteChitieuControl sqLiteChitieuControl;
    CheckBox cbMuon;
    CheckBox cbChomuon;
    CheckBox cbDatra;
    CheckBox cbChuatra;
    ListView lvChitieuVaymuon;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Chọn options");

        // groupId, itemId, order, title
        menu.add(0, MENU_EDIT, 0, "Đổi trạng thái");
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == MENU_EDIT){
            Chitieu selectedNote = (Chitieu) this.lvChitieuVaymuon.getItemAtPosition(info.position);
            if(selectedNote.getTrangthai().equals("Chưa trả")) {
                selectedNote.setTrangthai("Đã trả");
                selectedNote.setGhichu(selectedNote.getGiatri() + "");
                selectedNote.setGiatri(0);
            }
            sqLiteChitieuControl.updateChitieuNote(selectedNote);
            setAdapterChitieu(lvChitieuVaymuon,getActivity());
            ((BaseAdapter)lvChitieuVaymuon.getAdapter()).notifyDataSetChanged();
        }
        else {
            return false;
        }
        return true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);

        // Assign variable here
        sqLiteChitieuControl = new SQLiteChitieuControl(getContext());
        cbChomuon = view.findViewById(R.id.cb_Chomuon);
        cbMuon = view.findViewById(R.id.cb_Muon);
        cbDatra = view.findViewById(R.id.cb_Datra);
        cbChuatra = view.findViewById(R.id.cb_Chuatra);
        cbChuatra.setChecked(true);
        lvChitieuVaymuon = view.findViewById(R.id.lv_ChitieuVaymuon);

        // Call functions here
        setAdapterChitieu(lvChitieuVaymuon,getContext());
        registerForContextMenu(lvChitieuVaymuon);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setAdapterChitieu(final ListView mListView, final Context context){
        Log.e("setAdapterChitieu","start");
        ArrayList<Chitieu> list_Chitieu = sqLiteChitieuControl.getAllNoteChitieus1();
        final ArrayList<Chitieu> mainList = new ArrayList<>();
        for(int i = list_Chitieu.size() - 1 ; i >= 0 ; i --)
            if(list_Chitieu.get(i).getTenchitieu().equals("Cho mượn") || list_Chitieu.get(i).getTenchitieu().equals("Được cho mượn"))
                mainList.add(list_Chitieu.get(i));

        ChitieuListAdapter adapter = new ChitieuListAdapter(context,R.layout.list_chitieu_layout,mainList);
        mListView.setAdapter(adapter);

        cbMuon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) cbChomuon.setChecked(false);
                ChitieuListAdapter adapter = new ChitieuListAdapter(context, R.layout.list_chitieu_layout, getDataFilter(mainList));
                mListView.setAdapter(adapter);
                ((BaseAdapter)mListView.getAdapter()).notifyDataSetChanged();
            }
        });
        cbChomuon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) cbMuon.setChecked(false);
                ChitieuListAdapter adapter = new ChitieuListAdapter(context, R.layout.list_chitieu_layout, getDataFilter(mainList));
                mListView.setAdapter(adapter);
                ((BaseAdapter)mListView.getAdapter()).notifyDataSetChanged();
            }
        });
        cbDatra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) cbChuatra.setChecked(false);
                ChitieuListAdapter adapter = new ChitieuListAdapter(context, R.layout.list_chitieu_layout, getDataFilter(mainList));
                mListView.setAdapter(adapter);
                ((BaseAdapter)mListView.getAdapter()).notifyDataSetChanged();
            }
        });
        cbChuatra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) cbDatra.setChecked(false);
                ChitieuListAdapter adapter = new ChitieuListAdapter(context, R.layout.list_chitieu_layout, getDataFilter(mainList));
                mListView.setAdapter(adapter);
                ((BaseAdapter)mListView.getAdapter()).notifyDataSetChanged();
            }
        });

    }

    public ArrayList<Chitieu> getDataFilter(ArrayList<Chitieu> arrayList){
        ArrayList<Chitieu> listData = new ArrayList<>();
        String type = cbMuon.isChecked()? "Được cho mượn" : cbChomuon.isChecked()? "Cho mượn" : "";
        String status = cbDatra.isChecked()? "Đã trả" : cbChuatra.isChecked()? "Chưa trả" : "";
        for(int i = 0 ; i < arrayList.size() ; i ++){
            Chitieu obj = arrayList.get(i);
            if(type.equals("") && status.equals("")) listData.add(obj);
            else if(!type.equals("") && status.equals("")){
                if(obj.getTenchitieu().equals(type)) listData.add(obj);
            }
            else if(type.equals("") && !status.equals("")){
                if(obj.getTrangthai().equals(status)) listData.add(obj);
            }
            else {
                if(obj.getTenchitieu().equals(type) && obj.getTrangthai().equals(status)) listData.add(obj);
            }
        }
        return listData;
    }
}
