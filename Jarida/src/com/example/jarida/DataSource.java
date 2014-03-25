package com.example.jarida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.ronnyfriedland.shoppinglist.entity.Entry;
import de.ronnyfriedland.shoppinglist.entity.Shoppinglist;

/**
 * @author Ronny Friedland
 */
public class DataSource extends SQLiteOpenHelper {

    private static final String DB_NAME = "jarida.db";
    private static final Integer DB_VERSION = 4;
    private static DataSource datasource = null;
    
    private static final String TABLE_LOGIN = "login";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    /**
     * Return the (single) instance of {@link DataSource}.
     * 
     * @param context
     *            the base context of the app
     * @return instance of {@link DataSource}
     */
    public static DataSource getInstance(final Context context) {
        synchronized (DataSource.class) {
            if (null == datasource) {
                datasource = new DataSource(context);
            }
        }
        return datasource;
    }

    /**
     * {@inheritDoc}
     * 
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Entry.TABLE + "(" + Entry.COL_ID + " text primary key, "
                + Entry.COL_NAME + " text not null," + Entry.COL_ISSUE + " text not null, "
                + Entry.COL_CATEGORY + " text not null, " + Entry.COL_QUANTITY + " integer not null,"
                + Entry.COL_PRICE + " integer not null, " + Entry.COL_DESCRIPTION + " text not null,"
                + Entry.COL_IMGUrl + " text not null," +Entry.COL_ADDEDTOCART + " text not null)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Shoppinglist.TABLE + "(" + Shoppinglist.COL_ID
                + " text primary key)");
        
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")");
    }


    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        
    }

    private DataSource(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Creates a new {@link Entry}.
     * 
     * @param entry
     *            the entry to persist
     */
    public void createEntry(final Entry entry) {
        if (null != entry) {
        	 ContentValues values = new ContentValues();
             //values.put(Entry.COL_ID, entry.getUuid());
             values.put(Entry.COL_NAME, entry.getName());
             values.put(Entry.COL_ISSUE, entry.getIssue());
             values.put(Entry.COL_CATEGORY, entry.getCategory());
             values.put(Entry.COL_QUANTITY, entry.getQuantity());
             values.put(Entry.COL_PRICE, entry.getPrice());
             values.put(Entry.COL_DESCRIPTION, entry.getDescription());
             values.put(Entry.COL_IMGUrl, entry.getImgUrl());
             values.put(Entry.COL_ADDEDTOCART, entry.getAddedToCart().name());

            SQLiteDatabase database = getWritableDatabase();
            database.beginTransaction();
            try {
                database.insert(Entry.TABLE, null, values);
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
                database.close();
            }
        }
    }
    
    public void addUser(String name, String email, String uid, String created_at) {
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); 
        values.put(KEY_EMAIL, email); 
        values.put(KEY_UID, uid); 
        values.put(KEY_CREATED_AT, created_at); 
        
        SQLiteDatabase database = getWritableDatabase();
 
        database.beginTransaction();
        try {
            database.insert(TABLE_LOGIN, null, values);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            database.close();
        }
    }
    
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_LOGIN, new String[] { KEY_ID,KEY_NAME,KEY_EMAIL,KEY_UID, KEY_CREATED_AT}, null, null, null,
                null, null);
        try {
            if (cursor.moveToFirst()) {
            	  user.put(KEY_NAME, cursor.getString(1));
                  user.put(KEY_EMAIL, cursor.getString(2));
                  user.put(KEY_UID, cursor.getString(3));
                  user.put(KEY_CREATED_AT, cursor.getString(4));
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            database.close();
        }
        return user;
    }
    
    public int getLoginRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        int rowCount;
        SQLiteDatabase database = getReadableDatabase();
        
        Cursor cursor =  database.query(TABLE_LOGIN, new String[] { KEY_ID,KEY_NAME,KEY_EMAIL,KEY_UID, KEY_CREATED_AT}, null, null, null,
                null, null);
        
        try {
        	rowCount = cursor.getCount();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            database.close();
        }
        return rowCount;
    }
    
    public void resetLoginTable(){
        SQLiteDatabase database = getWritableDatabase();

        try {
        	database.delete(TABLE_LOGIN, null, null);
        } finally {
            database.close();
        }
    }
    
    
    

    /**
     * Updates the given entry based on the uuid.
     * 
     * @param entry
     *            the {@link Entry} to update
     */
    public void updateEntry(final Entry entry) {
        if (null != entry) {
            ContentValues values = new ContentValues();
            values.put(Entry.COL_NAME, entry.getName());
            values.put(Entry.COL_ISSUE, entry.getIssue());
            values.put(Entry.COL_CATEGORY, entry.getCategory());
            values.put(Entry.COL_QUANTITY, entry.getQuantity());
            values.put(Entry.COL_PRICE, entry.getPrice());
            values.put(Entry.COL_DESCRIPTION, entry.getDescription());
            values.put(Entry.COL_IMGUrl, entry.getImgUrl());
            values.put(Entry.COL_ADDEDTOCART, entry.getAddedToCart().name());

            SQLiteDatabase database = getWritableDatabase();
            database.beginTransaction();
            try {
                database.update(Entry.TABLE, values, Entry.COL_ID + "=?", new String[] { String.valueOf(entry.getUuid()) });
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * Retrieves a list of {@link Entry} stored in the database
     * 
     * @return list of all entries
     */
    public List<Entry> getEntries() {
        List<Entry> entries = new ArrayList<Entry>();
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(Entry.TABLE, new String[] { Entry.COL_ID, Entry.COL_NAME,
                Entry.COL_ISSUE, Entry.COL_CATEGORY, Entry.COL_QUANTITY, Entry.COL_PRICE, Entry.COL_DESCRIPTION,
                Entry.COL_IMGUrl,Entry.COL_ADDEDTOCART}, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Entry entry = new Entry(cursor.getInt(0));
                    entry.setName(cursor.getString(1));
                    entry.setIssue(cursor.getInt(2));
                    entry.setCategory(cursor.getString(3));
                    entry.setQuantity(cursor.getInt(4));
                    entry.setPrice(cursor.getInt(5));
                    entry.setDescription(cursor.getString(6));
                    entry.setimgUrl(cursor.getString(7));
                    entry.setAddedToCart(cursor.getString(8));
                    entries.add(entry);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            database.close();
        }
        return entries;
    }

    /**
     * Retrieves the current {@link Entry}.
     * 
     * @param uuid
     *            the uuid of the entry
     * 
     * @return the current {@link Entry}
     */
    public Entry getEntry(final String uuid) {
        Entry entry = null;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(Entry.TABLE, new String[] {  Entry.COL_ID, Entry.COL_NAME,
                Entry.COL_ISSUE, Entry.COL_CATEGORY, Entry.COL_QUANTITY, Entry.COL_PRICE, Entry.COL_DESCRIPTION,
                Entry.COL_IMGUrl,Entry.COL_ADDEDTOCART}, Entry.COL_ID + "=?", new String[] { uuid }, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                entry = new Entry(cursor.getInt(0));
                entry.setName(cursor.getString(1));
                entry.setIssue(cursor.getInt(2));
                entry.setCategory(cursor.getString(3));
                entry.setQuantity(cursor.getInt(4));
                entry.setPrice(cursor.getInt(5));
                entry.setDescription(cursor.getString(6));
                entry.setimgUrl(cursor.getString(7));
                entry.setAddedToCart(cursor.getString(8));
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            database.close();
        }
        return entry;
    }

    /**
     * Deletes the given {@link Entry}.
     * 
     * @param entry
     *            the {@link Entry} to delete
     */
    public void deleteEntry(final Entry entry) {
        if (null != entry) {
            SQLiteDatabase database = getWritableDatabase();
            database.beginTransaction();
            try {
                database.delete(Entry.TABLE, Entry.COL_ID + "=?", new String[] { String.valueOf(entry.getUuid()) });
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * Deletes every {@link Entry} which is associated to the given
     * {@link Shoppinglist}.
     * 
     * @param list
     *            the associated {@link Shoppinglist}.
     */
    public void deleteEntry(final Shoppinglist list) {
        if (null != list) {
            SQLiteDatabase database = getWritableDatabase();
            database.beginTransaction();
            try {
                database.delete(Entry.TABLE, Entry.COL_LIST + "=?", new String[] { String.valueOf(list.getUuid()) });
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * Deletes the {@link Shoppinglist} given as parameter.
     */
    public void deleteList() {
        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(Shoppinglist.TABLE, null, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            database.close();
        }
    }

    /**
     * Retrieves the current {@link Shoppinglist}.
     * 
     * @return the current {@link Shoppinglist}
     */
    public Shoppinglist getList() {
        Shoppinglist list = null;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(Shoppinglist.TABLE, new String[] { Shoppinglist.COL_ID }, null, null, null,
                null, null);
        try {
            if (cursor.moveToFirst()) {
                list = new Shoppinglist(cursor.getInt(0));
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            database.close();
        }
        return list;
    }

    /**
     * Creates a new {@link Shoppinglist}.
     * 
     * @param list
     *            the {@link Shoppinglist} to create
     */
    public void createList(final Shoppinglist list) {
        if (null != list) {
            ContentValues values = new ContentValues();
            values.put(Shoppinglist.COL_ID, list.getUuid());

            SQLiteDatabase database = getWritableDatabase();
            database.beginTransaction();
            try {
                database.insert(Shoppinglist.TABLE, null, values);
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
                database.close();
            }
        }
    }
}
