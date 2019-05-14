package com.example.lifemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class SQLiteChitieuControl extends SQLiteOpenHelper{
    private static final String TAG = "SQLiteTuyen";
    // Phiên bản
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MY_DB";

    // chi tieu
    private static final String TABLE_NOTE = "chitieu";

    private static final String COLUMN_NOTE_NGAYGHI ="ngayghi";
    private static final String COLUMN_NOTE_LOAICHITIEU="loaichitieu";
    private static final String COLUMN_NOTE_GIATRI = "giatri";
    private static final String COLUMN_NOTE_TENCHITIEU ="tenchitieu";
    private static final String COLUMN_NOTE_GHICHU ="ghichu";
    private static final String COLUMN_NOTE_TRANGTHAI = "trangthai";

    public SQLiteChitieuControl(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreateTuyen ... ");
        String scriptTuyen = "CREATE TABLE IF NOT EXISTS "    + TABLE_NOTE + "("
                + COLUMN_NOTE_NGAYGHI           + " VARCHAR PRIMARY KEY,"
                + COLUMN_NOTE_LOAICHITIEU       + " VARCHAR,"
                + COLUMN_NOTE_GIATRI            + " INTEGER,"
                + COLUMN_NOTE_TENCHITIEU        + " VARCHAR,"
                + COLUMN_NOTE_GHICHU            + " VARCHAR,"
                + COLUMN_NOTE_TRANGTHAI         + " VARCHAR"
                + ")";

        // Chạy lệnh tạo bảng.
        db.execSQL(scriptTuyen);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME+"."+ TABLE_NOTE);

        // Và tạo lại.
        onCreate(db);
    }

    public void addNoteChitieu(Chitieu note) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getTenchitieu());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_NGAYGHI, note.getNgayghi());
        values.put(COLUMN_NOTE_LOAICHITIEU, note.getLoaichitieu());
        values.put(COLUMN_NOTE_GIATRI, note.getGiatri());
        values.put(COLUMN_NOTE_TENCHITIEU, note.getTenchitieu());
        values.put(COLUMN_NOTE_GHICHU, note.getGhichu());
        values.put(COLUMN_NOTE_TRANGTHAI, note.getTrangthai());
        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_NOTE, null, values);

        // Đóng kết nối database.
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Chitieu getNoteChitieubyNgayghi(String ngayghi){
        Log.i(TAG, "MyDatabaseHelper.getNoteTuyen ... " + ngayghi);
        Chitieu note = new Chitieu();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NOTE, new String[]{
                        COLUMN_NOTE_NGAYGHI,
                        COLUMN_NOTE_LOAICHITIEU,
                        COLUMN_NOTE_GIATRI,
                        COLUMN_NOTE_TENCHITIEU,
                        COLUMN_NOTE_GHICHU,
                        COLUMN_NOTE_TRANGTHAI}, COLUMN_NOTE_NGAYGHI + "=?",
                new String[]{String.valueOf(ngayghi)}, null, null, null, null)) {

            if (cursor != null)
                cursor.moveToFirst();

            note.setNgayghi(cursor.getString(0));
            note.setLoaichitieu(cursor.getString(1));
            note.setGiatri(cursor.getInt(2));
            note.setTenchitieu(cursor.getString(3));
            note.setGhichu(cursor.getString(4));
            note.setTrangthai(cursor.getString(5));
        }

        // return note
        db.close();
        return note;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Map<String,Chitieu> getAllNoteChitieus() {
        Log.i(TAG, "MyDatabaseHelper.getAllTuyenNotes ... " );

        Map<String,Chitieu> noteList = new HashMap<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();


        // Duyệt trên con trỏ, và thêm vào danh sách.
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Chitieu note = new Chitieu();
                    note.setNgayghi(cursor.getString(0));
                    note.setLoaichitieu(cursor.getString(1));
                    note.setGiatri(cursor.getInt(2));
                    note.setTenchitieu(cursor.getString(3));
                    note.setGhichu(cursor.getString(4));
                    note.setTrangthai(cursor.getString(5));
                    // Thêm vào danh sách.
                    noteList.put(note.getNgayghi(),note);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        // return note list
        return noteList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<Chitieu> getAllNoteChitieus1() {
        Log.i(TAG, "MyDatabaseHelper.getAllTuyenNotes ... " );

        ArrayList<Chitieu> noteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();


        // Duyệt trên con trỏ, và thêm vào danh sách.
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Chitieu note = new Chitieu();
                    note.setNgayghi(cursor.getString(0));
                    note.setLoaichitieu(cursor.getString(1));
                    note.setGiatri(cursor.getInt(2));
                    note.setTenchitieu(cursor.getString(3));
                    note.setGhichu(cursor.getString(4));
                    note.setTrangthai(cursor.getString(5));
                    // Thêm vào danh sách.
                    noteList.add(note);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        // return note list
        return noteList;
    }

    public int getNoteChitieusCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();
        // return count
        return count;
    }

    public int updateChitieuNote(Chitieu note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... "  + note.getNgayghi());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_NGAYGHI, note.getNgayghi());
        values.put(COLUMN_NOTE_LOAICHITIEU, note.getLoaichitieu());
        values.put(COLUMN_NOTE_GIATRI, note.getGiatri());
        values.put(COLUMN_NOTE_TENCHITIEU, note.getTenchitieu());
        values.put(COLUMN_NOTE_GHICHU, note.getGhichu());
        values.put(COLUMN_NOTE_TRANGTHAI, note.getTrangthai());
        // updating row
        return db.update(TABLE_NOTE, values, COLUMN_NOTE_NGAYGHI + " = ?",
                new String[]{String.valueOf(note.getNgayghi())});
    }

    public void deleteChitieuNote(Chitieu note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNgayghi() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_NOTE_NGAYGHI+ " = ?",
                new String[] { String.valueOf(note.getNgayghi()) });
        db.close();
    }

    public  void deleteAllChitieu() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NOTE);
        db.close();
    }
}
