package com.example.mycashbook.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "my_cash_book.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_KEUANGAN = "keuangan";
    private static final String COLUMN_ID_KEUANGAN = "id_keuangan";
    private static final String COLUMN_TANGGAL = "tanggal";
    private static final String COLUMN_NOMINAL = "nominal";
    private static final String COLUMN_KETERANGAN = "keterangan";
    private static final String COLUMN_KATEGORI = "kategori";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_KEUANGAN +
                " (" + COLUMN_ID_KEUANGAN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TANGGAL + " TEXT, " +
                COLUMN_NOMINAL + " TEXT, " +
                COLUMN_KETERANGAN + " TEXT, " +
                COLUMN_KATEGORI + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEUANGAN);
        onCreate(db);
    }

    public void addKeuangan(String tanggal, String nominal, String keterangan, String kategori) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TANGGAL, tanggal);
        cv.put(COLUMN_NOMINAL, nominal);
        cv.put(COLUMN_KETERANGAN, keterangan);
        cv.put(COLUMN_KATEGORI, kategori);

        long result = db.insert(TABLE_KEUANGAN, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Data gagal ditambahkan!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_KEUANGAN
                + " ORDER BY " + COLUMN_ID_KEUANGAN
                + " DESC ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public String getRangkumanKeuangan(String kategori) {
        String result = "0";
        String query = "SELECT SUM(nominal) AS total FROM keuangan where kategori = '" + kategori + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            if (cursor.moveToFirst()) {
                if (cursor.isNull(0)) {
                    result = "0";
                } else {
                    result = cursor.getString(0);
                }
            }
        }

        return result;
    }


}
