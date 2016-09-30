package sg.edu.ntu.cz3002.enigma.eclinic.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Database helper to handle all db related staffs
 */
public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "eClinic.db";
    public static final String TABLE_NAME = "ChatTable";
    public static final String COLUMN_NAME_RECEIVER = "RECEIVER";
    public static final String COLUMN_NAME_SENDER = "SENDER";
    public static final String COLUMN_NAME_MSG = "MESSAGE";
    public static final String COLUMN_NAME_TIME = "TIME";
    public static final String COLUMN_NAME_ID = "ID";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private String selection = null;
    private String[] selectionValue = null;
    private String groupBy = null;
    private String having = null;
    private String[] projection = {
            COLUMN_NAME_ID,
            COLUMN_NAME_RECEIVER,
            COLUMN_NAME_SENDER,
            COLUMN_NAME_MSG,
            COLUMN_NAME_TIME
    };
    private String sortOrder = COLUMN_NAME_TIME + " DESC";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_NAME_ID+ " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TIME + " DATETIME" + COMMA_SEP +
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

    public void setSelection(String s){
        this.selection = s;
    }

    public void setGroupBy(String s){
        this.groupBy = s;
    }

    public void setHaving(String s) { this.having = s;}

    public void setSelectionValue(String[] s){
        this.selectionValue = s;
    }

    public boolean insertDb (String receiver, String sender, String msg, String time){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("INSERT", msg);
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_RECEIVER, receiver);
        values.put(COLUMN_NAME_SENDER, sender);
        values.put(COLUMN_NAME_MSG, msg);
        values.put(COLUMN_NAME_TIME, time);
        db.insert(TABLE_NAME, null, values);
        // Insert the new row, returning the primary key value of the new row
        //long newRowId = db.insert(TABLE_NAME, null, values);
        return true;
    }

    public Cursor readDb(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(
                TABLE_NAME,                               // The table to query
                projection,                                     // "*" select all col
                selection,                                // The columns for the WHERE clause
                selectionValue,                            // The values for the WHERE clause
                groupBy,                                     // group the rows
                having,                                     // filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        return c;
    }

    public List getChatHistoryList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor _c = loadUser();
        String _s;
        List<String> _result = new ArrayList<>();
        if(_c == null)
            return _result;
        while(true){
            _s = _c.getString(_c.getColumnIndex("USER"));
            Cursor _return = db.query(TABLE_NAME, projection,
                    "SENDER = ? OR RECEIVER = ?",
                    new String[] {_s, _s},
                    null, null, sortOrder, "1");
            _return.moveToFirst();
            _result.add(_s);
            _result.add(_return.getString(_return.getColumnIndex(COLUMN_NAME_MSG)));
            if(_c.isLast())
                return _result;
            _c.moveToNext();
        }
    }

    public Cursor loadUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT RECEIVER AS USER " +
                "FROM ChatTable" +
                " UNION " +
                "SELECT SENDER AS USER " +
                "FROM ChatTable";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        if(c.getCount() == 0){
            Log.d("C:", "nothing");
            return null;
        }
        return c;
    }
}
