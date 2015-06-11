package com.example.binyamin.fakebook;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.internal.cu;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Manager";

    // Contacts table name
    public static final String TABLE_MESSAGES = "messages";
    private static final String TABLE_CHANNELS = "channels";


    // Contacts Table Columns names
    public static final String KEY_CHANNEL_ID = "channel_id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_TEXT = "text";
    public static final String KEY_DATE_TIME = "date_time";
    public static final String KEY_LONGTITUDE = "longtitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_ICON = "icon";
    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + KEY_CHANNEL_ID + " TEXT," + KEY_USER_ID + " TEXT,"
                + KEY_TEXT + " TEXT," + KEY_DATE_TIME + " TEXT,"
                + KEY_LONGTITUDE + " TEXT," + KEY_LATITUDE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

        CREATE_TABLE = "CREATE TABLE " + TABLE_CHANNELS + "("
                + KEY_ICON + " TEXT," + KEY_NAME + " TEXT," + KEY_ID + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNELS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new message
    void addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_CHANNEL_ID, message.getChanel());
        values.put(KEY_USER_ID, message.getUser());
        values.put(KEY_TEXT, message.getText());
        values.put(KEY_DATE_TIME, message.getDate_time());
        values.put(KEY_LONGTITUDE, message.getLongitude());
        values.put(KEY_LATITUDE, message.getLatitude());


        // Inserting Row
        db.insert(TABLE_MESSAGES, null, values);
        db.close(); // Closing database connection
    }

    // Adding new message
    void addChannel(Channel channel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ICON, channel.getIcon());
        values.put(KEY_NAME, channel.getName());
        values.put(KEY_ID, channel.getId());

        // Inserting Row
        db.insert(TABLE_MESSAGES, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Messages
    public List<Message> getAllContacts() {
        List<Message> messageList = new ArrayList<Message>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setChannel(cursor.getString(0));
                message.setUser(cursor.getString(1));
                message.setText(cursor.getString(2));
                message.setDate_time(cursor.getString(3));
                message.setLongitude(cursor.getString(4));
                message.setLatitude(cursor.getString(5));
                // Adding contact to list
                messageList.add(message);
            } while (cursor.moveToNext());
        }

        // return contact list
        return messageList;
    }


}