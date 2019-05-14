package com.example.lifemanagement;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class ChitieuFormFragment extends Fragment {
    // Menu
        private final int MENU_REASON_1 = 111;
        private final int MENU_REASON_2 = 222;
        private final int MENU_REASON_3 = 333;
        private final int MENU_REASON_4 = 444;
        private final int MENU_REASON_5 = 555;
        private final int MENU_REASON_6 = 666;
        private final int MENU_REASON_7 = 777;
        private final int MENU_REASON_8 = 888;
    // Object
    SQLiteChitieuControl sqLiteChitieuControl;
    Button btnAbsolute;
    Button btnClose;
    CheckBox cbLoaichitieu;
    TextView tvLoaichitieu;
    CheckBox cbTenchitieu;
    EditText etTenchitieu;
    EditText etGiatri;
    EditText etGhichu;
    public ChitieuFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Chọn tên chi tiêu");

        // groupId, itemId, order, title
        menu.add(0, MENU_REASON_1, 0, "Ăn uống");
        menu.add(0, MENU_REASON_2, 2, "Giải trí");
        menu.add(0, MENU_REASON_3, 4, "Mua sắm");
        menu.add(0, MENU_REASON_5, 6, "Tiêu dùng hàng tháng");
        menu.add(0, MENU_REASON_4, 8, "Công việc");
        menu.add(0, MENU_REASON_6, 10, "Thu nhập");
        menu.add(0, MENU_REASON_7, 12, "Chi phí phát sinh");
        menu.add(0, MENU_REASON_7, 14, "Cho mượn");
        menu.add(0, MENU_REASON_7, 16, "Được cho mượn");
        menu.add(0, MENU_REASON_8, 18, "Khác (Vui lòng ghi rõ)");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        etTenchitieu.setText(item.getTitle().toString());
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chitieu_form, container, false);
        // Assign variable here
        sqLiteChitieuControl = new SQLiteChitieuControl(getContext());
        cbLoaichitieu = view.findViewById(R.id.cb_loaichitieu);
        cbTenchitieu = view.findViewById(R.id.cb_Tenchitieu);
        etTenchitieu = view.findViewById(R.id.et_Tenchitieu);
        etTenchitieu.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etGhichu = view.findViewById(R.id.et_Ghichu);
        etGhichu.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etGiatri = view.findViewById(R.id.et_Giatri);
        etGiatri.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tvLoaichitieu = view.findViewById(R.id.tv_Loaichitieu);
        tvLoaichitieu.setTextColor(Color.RED);
        btnAbsolute = view.findViewById(R.id.btn_Absolute) ;
        btnClose = view.findViewById(R.id.btn_Close);


        // Call functions here
        cbLoaichitieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    tvLoaichitieu.setTextColor(Color.GREEN);
                    tvLoaichitieu.setText("Thu nhập");
                }
                else
                {
                    tvLoaichitieu.setTextColor(Color.RED);
                    tvLoaichitieu.setText("Chi tiêu");
                }
            }
        });

        cbTenchitieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    registerForContextMenu(buttonView);
                    Objects.requireNonNull(getActivity()).openContextMenu(buttonView);
                }
                else
                {
                    etTenchitieu.setText("");
                }
            }
        });

        btnAbsolute.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                // Adding in SQLite
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm");
                String formattedDate = df.format(currentTime);
                String status = "";
                if(etTenchitieu.getText().toString().equals("Cho mượn") || etTenchitieu.getText().toString().equals("Được cho mượn")) status = "Chưa trả";
                sqLiteChitieuControl.addNoteChitieu(new Chitieu(
                        formattedDate,
                        tvLoaichitieu.getText().toString(),
                        Integer.parseInt(etGiatri.getText().toString()),
                        etTenchitieu.getText().toString(),
                        etGhichu.getText().toString(),
                        status
                ));


                // Close
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                ft.detach(ChitieuFormFragment.this);
                ft.commit();
                HistoryFragment.btnAddChitieu.setVisibility(View.VISIBLE);
                UserActivity.navigation.setVisibility(View.VISIBLE);

                FragmentTransaction ft2 = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                ft2.detach(HistoryFragment.hFragment);
                ft2.attach(HistoryFragment.hFragment);
                ft2.commit();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                ft.detach(ChitieuFormFragment.this);
                ft.commit();
                HistoryFragment.btnAddChitieu.setVisibility(View.VISIBLE);
                UserActivity.navigation.setVisibility(View.VISIBLE);
            }
        });
        // Inflate the layout for this fragment
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

}
