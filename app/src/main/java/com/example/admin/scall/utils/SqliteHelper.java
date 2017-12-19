package com.example.admin.scall.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.scall.model.InfoStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/27/2017.
 */

public class SqliteHelper extends SQLiteOpenHelper {
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        openDb();
    }

    private SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "editStyle";

    // Contacts table name
    private static final String TABLE_STYLE = "style";
    private String DB_PATH = "data/data/com.example.admin.scall/databases/";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone_number";
    private static final String KEY_FONT = "font";
    private static final String KEY_URL_IMAGE = "url_image";
    private static final String KEY_COLOR = "color";
    private static final String KEY_SIZE = "size";
    private static final String KEY_ANIMATION = "animation";
    private static final String KEY_ICON = "icon";
    private static final String KEY_FULL = "full";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_STYLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_PHONE + " TEXT," + KEY_FONT + " TEXT," + KEY_URL_IMAGE + " TEXT,"
                + KEY_COLOR + " INTEGER," + KEY_SIZE + " INTEGER," + KEY_ANIMATION + " INTEGER,"
                + KEY_ICON + " TEXT," + KEY_FULL + " INTEGER )";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STYLE);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    private void openDb() {
//        String myPath = DB_PATH + TABLE_STYLE;
//        db = SQLiteDatabase.openDatabase(myPath, null,
//                SQLiteDatabase.OPEN_READWRITE);
    }

    public void addStyle(InfoStyle style) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(KEY_ID, style.getId());
        values.put(KEY_NAME, style.getName()); // Contact Name
        values.put(KEY_PHONE, style.getPhone()); // Contact Phone
        values.put(KEY_FONT, style.getFont()); // Contact Phone
        values.put(KEY_URL_IMAGE, style.getUrlImage()); // Contact Phone
        values.put(KEY_COLOR, style.getColor()); // Contact Phone
        values.put(KEY_SIZE, style.getSize()); // Contact Phone
        values.put(KEY_ANIMATION, style.getAnimation()); // Contact Phone
        values.put(KEY_ICON, style.getListIcon());
        values.put(KEY_FULL, style.getIsFull());
        // Inserting Row
        db.insert(TABLE_STYLE, null, values);
        db.close(); // Closing database connection
    }

    public InfoStyle getStyleById(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STYLE, new String[]{KEY_ID,
                        KEY_NAME, KEY_PHONE, KEY_FONT, KEY_URL_IMAGE, KEY_COLOR, KEY_SIZE, KEY_ANIMATION, KEY_ICON, KEY_FULL}, KEY_ID + "=?",
                new String[]{phone}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        InfoStyle style = new InfoStyle(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), cursor.getString(8), Integer.parseInt(cursor.getString(9)));
        // return contact
        return style;
    }

    public InfoStyle getStyleByPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STYLE, new String[]{KEY_ID,
                        KEY_NAME, KEY_PHONE, KEY_FONT, KEY_URL_IMAGE, KEY_COLOR, KEY_SIZE, KEY_ANIMATION, KEY_ICON, KEY_FULL}, KEY_PHONE + "=?",
                new String[]{phone}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        InfoStyle style = new InfoStyle(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), cursor.getString(8), Integer.parseInt(cursor.getString(9)));
        // return contact
        return style;
    }

    public List<InfoStyle> getAllStyle() {
        List<InfoStyle> contactList = new ArrayList<InfoStyle>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STYLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InfoStyle style = new InfoStyle();
                style.setId(Integer.parseInt(cursor.getString(0)));
                style.setName(cursor.getString(1));
                style.setPhone(cursor.getString(2));
                style.setFont(cursor.getString(3));
                style.setUrlImage(cursor.getString(4));
                style.setColor(cursor.getInt(5));
                style.setSize(cursor.getInt(6));
                style.setAnimation(cursor.getInt(7));
                style.setListIcon(cursor.getString(8));
                style.setIsFull(cursor.getInt(9));
                // Adding contact to list
                contactList.add(style);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public int updateStyle(InfoStyle style) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_ID, style.getId());
        values.put(KEY_NAME, style.getName());
        values.put(KEY_PHONE, style.getPhone());
        values.put(KEY_FONT, style.getFont());
        values.put(KEY_URL_IMAGE, style.getUrlImage());
        values.put(KEY_COLOR, style.getColor());
        values.put(KEY_SIZE, style.getSize());
        values.put(KEY_ANIMATION, style.getAnimation());
        values.put(KEY_ICON, style.getListIcon());
        values.put(KEY_FULL, style.getIsFull());
        // updating row
        return db.update(TABLE_STYLE, values, KEY_ID + " = ? ",
                new String[]{String.valueOf(style.getId())});
    }

    public void deleteStyle(InfoStyle style) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STYLE, KEY_ID + " = ?",
                new String[]{String.valueOf(style.getId())});
        db.close();
    }

    public int getStyleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STYLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


}
