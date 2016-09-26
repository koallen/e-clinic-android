package sg.edu.ntu.cz3002.enigma.eclinic.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Database helper to handle all db related staffs
 */
public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "eClinic.db";
    public static final String TABLE_NAME = "ChatTable";
    public static final String COLUMN_NAME_RECEIVER = "Receiver";
    public static final String COLUMN_NAME_SENDER = "Sender";
    public static final String COLUMN_NAME_MSG = "Message";
    public static final String COLUMN_NAME_TIME = "Time";
    public static final String COLUMN_NAME_ID = "ID";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_NAME_ID+ " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_RECEIVER + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_SENDER + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_MSG + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate","--- create table ---");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertDb (String receiver, String sender, String msg){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("INSERT", msg);
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_RECEIVER, receiver);
        values.put(COLUMN_NAME_SENDER, sender);
        values.put(COLUMN_NAME_MSG, msg);
        values.put(COLUMN_NAME_TIME, new Date().toString());
        db.insert(TABLE_NAME, null, values);
        // Insert the new row, returning the primary key value of the new row
        //long newRowId = db.insert(TABLE_NAME, null, values);
        return true;
    }

    public String readDb(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_ID,
                COLUMN_NAME_RECEIVER,
                COLUMN_NAME_SENDER,
                COLUMN_NAME_MSG,
                COLUMN_NAME_TIME
        };

        String selection = "ROWNUM = 1";
        String[] selectionArgs = { "My Title" };

        String sortOrder = COLUMN_NAME_TIME + " DESC";

        Cursor c = db.query(
                TABLE_NAME,                               // The table to query
                projection,                                     // "*" select all col
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        return c.getString(c.getColumnIndex(COLUMN_NAME_SENDER)) + " : " +
                c.getString(c.getColumnIndex(COLUMN_NAME_MSG));
    }
}
