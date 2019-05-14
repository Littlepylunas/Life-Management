package com.example.lifemanagement;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;

public class DetailsActivity extends AppCompatActivity {
    EditText etFindChitieuDetails;
    ListView lvChitieuDetails;
    SQLiteChitieuControl sqLiteChitieuControl;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // Assign variable here
        sqLiteChitieuControl = new SQLiteChitieuControl(DetailsActivity.this);
        etFindChitieuDetails = findViewById(R.id.et_FindChitieuDetails);
        etFindChitieuDetails.setImeOptions(EditorInfo.IME_ACTION_DONE);
        lvChitieuDetails = findViewById(R.id.lv_ChitieuDetails);


        // Call function here
        setAdapterChitieu(lvChitieuDetails,DetailsActivity.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setAdapterChitieu(final ListView mListView, final Context context){
        String type = getIntent().getStringExtra("TYPE");
        String date = getIntent().getStringExtra("DATE");
        Log.e("setAdapterChitieu","start");
        ArrayList<Chitieu> list_Chitieu = sqLiteChitieuControl.getAllNoteChitieus1();
        final ArrayList<Chitieu> mainList = new ArrayList<>();
        for(int i = list_Chitieu.size() - 1 ; i >= 0 ; i --)
            if(list_Chitieu.get(i).getLoaichitieu().equals(type) && list_Chitieu.get(i).getNgayghi().contains(date) )
                mainList.add(list_Chitieu.get(i));

        ChitieuListAdapter adapter = new ChitieuListAdapter(context,R.layout.list_chitieu_layout,mainList);
        mListView.setAdapter(adapter);

        final ArrayList<Chitieu> filterList = new ArrayList<>();
        filterList.clear();
        filterList.addAll(mainList);
        etFindChitieuDetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList.clear();
                s = s.toString().toLowerCase();

                for(int i = 0 ; i < mainList.size(); i ++){
                    if(mainList.get(i).getNgayghi().contains(s)) filterList.add(mainList.get(i));
                }

                ChitieuListAdapter adapter = new ChitieuListAdapter(context, R.layout.list_chitieu_layout, filterList);
                mListView.setAdapter(adapter);
                ((BaseAdapter)mListView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
