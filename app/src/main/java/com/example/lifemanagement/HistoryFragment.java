package com.example.lifemanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;


public class HistoryFragment extends Fragment {
    // Menu
        private final int MENU_EDIT = 100;
        private final int MENU_DELETE = 200;
    //
    public static Fragment hFragment ;
    @SuppressLint("StaticFieldLeak")
    public static Button btnAddChitieu;
    ListView lvChitieu;
    EditText etFindChitieu;
    SQLiteChitieuControl sqLiteChitieuControl;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Chọn options");

        // groupId, itemId, order, title
        menu.add(0, MENU_EDIT, 0, "Chỉnh sửa");
        menu.add(0, MENU_DELETE, 2, "Xóa");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        if(item.getItemId() == MENU_EDIT){
            Chitieu selectedNote = (Chitieu) this.lvChitieu.getItemAtPosition(info.position);
            //etLydo.setText("Thay thế dồng hồ");
        }
        else if(item.getItemId() == MENU_DELETE ){
            // Hỏi trước khi xóa.
            final Chitieu selectedNote = (Chitieu) this.lvChitieu.getItemAtPosition(info.position);
            new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setMessage("Bạn chắc chắn muốn xóa bản ghi " + selectedNote.getNgayghi())
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sqLiteChitieuControl.deleteChitieuNote (selectedNote);
                            // Reload current fragment
                            setAdapterChitieu(lvChitieu,getActivity());
                            ((BaseAdapter)lvChitieu.getAdapter()).notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hFragment = HistoryFragment.this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        // Assign variable here
        sqLiteChitieuControl = new SQLiteChitieuControl(getActivity());
        btnAddChitieu = view.findViewById(R.id.btn_AddChitieu);
        btnAddChitieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment addform = new ChitieuFormFragment();
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame2,addform);
                ft.commit();
                btnAddChitieu.setVisibility(View.INVISIBLE);
                UserActivity.navigation.setVisibility(View.INVISIBLE);
            }
        });
        lvChitieu = view.findViewById(R.id.lv_Chitieu);
        etFindChitieu = view.findViewById(R.id.et_FindChitieu);
        etFindChitieu.setImeOptions(EditorInfo.IME_ACTION_DONE);
        // Call functions here
        setAdapterChitieu(lvChitieu,getActivity());
        registerForContextMenu(lvChitieu);
        // Return
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
    public void setAdapterChitieu(final ListView mListView,final Context context){
        Log.e("setAdapterChitieu","start");
        ArrayList<Chitieu> list_Chitieu = sqLiteChitieuControl.getAllNoteChitieus1();
        final ArrayList<Chitieu> mainList = new ArrayList<>();
        for(int i = list_Chitieu.size() - 1 ; i >= 0 ; i --) mainList.add(list_Chitieu.get(i));

        ChitieuListAdapter adapter = new ChitieuListAdapter(context,R.layout.list_chitieu_layout,mainList);
        mListView.setAdapter(adapter);

        final ArrayList<Chitieu> filterList = new ArrayList<>();
        filterList.clear();
        filterList.addAll(mainList);
        etFindChitieu.addTextChangedListener(new TextWatcher() {
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
