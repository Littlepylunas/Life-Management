package com.example.easymanage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DetailActivity extends AppCompatActivity {
    private final int MENU_REASON_1 = 111;
    private final int MENU_REASON_2 = 222;
    private final int MENU_REASON_3 = 333;
    private final int MENU_REASON_4 = 444;
    private final int MENU_REASON_5 = 555;
    private final int MENU_REASON_6 = 666;
    private final int MENU_REASON_7 = 777;
    private final int MENU_REASON_8 = 888;
    public static final int CAMERA_REQUEST = 9999;
    public static Bitmap bitmap_static;
    ImageButton img_btn1 ;
    TextView tv_Verify;
    Button btnSave;
    Button btnEdit;
    TextView tvName;
    TextView tvmaKH;
    TextView tvAddress;
    TextView tvChisocu;
    EditText etChisomoi;
    TextView tvStatus;
    TextView tvTieuthuTB ;
    TextView tv_Verify2;
    TextView tv_LoaiKH;
    EditText etLydo;
    EditText etGhichu;
    TextView tv_LoaiDH;
    TextView tv_Sodt;
    TextView tvTieuthu;
    CheckBox cbDutchi;




    @Override
    public void onBackPressed() {
        Intent intent1 = getIntent();
        String type = intent1.getStringExtra("TYPE");
        Intent intentBack1 = new Intent(DetailActivity.this,ListDataActivity.class);
        Intent intentBack2 = new Intent(DetailActivity.this,DataSortActivity.class);
        if(type.equals("chart"))
        {
            String status2 = intent1.getStringExtra("TRANG_THAI");

            if(status2.equals("Chưa ghi"))
            {
                intentBack1.putExtra("STATUS",false);
            }else{
                intentBack1.putExtra("STATUS",true);
            }
            startActivity(intentBack1);
        }
        else
        {
            int[] data = intent1.getIntArrayExtra("DATA");
            String name = intent1.getStringExtra("TENTUYEN");
            intentBack2.putExtra("DATA",data);
            intentBack2.putExtra("TENTUYEN",name);
            startActivity(intentBack2);
        }


        super.onBackPressed();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Chọn lý do đứt chì");

        // groupId, itemId, order, title
        menu.add(0, MENU_REASON_1, 0, "Thay thế đồng hồ");
        menu.add(0, MENU_REASON_2, 2, "Nâng,hạ, di chuyển ĐH");
        menu.add(0, MENU_REASON_3, 4, "Kiểm tra đồng hồ");
        menu.add(0, MENU_REASON_4, 6, "Ô xi hóa");
        menu.add(0, MENU_REASON_5, 8, "Vệ sinh đồng hồ");
        menu.add(0, MENU_REASON_6, 10, "Cắt, mở nước cho KH");
        menu.add(0, MENU_REASON_7, 12, "Sửa chữa, thông tắc");
        menu.add(0, MENU_REASON_8, 14, "Chuột cắn");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == MENU_REASON_1){
            etLydo.setText("Thay thế dồng hồ");
        }
        else i