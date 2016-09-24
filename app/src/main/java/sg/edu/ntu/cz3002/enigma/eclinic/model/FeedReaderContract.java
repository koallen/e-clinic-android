package sg.edu.ntu.cz3002.enigma.eclinic.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by ZWL on 24/9/16.
 */
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "ChatTable";
        public static final String COLUMN_NAME_RECEIVER = "Receiver";
        public static final String COLUMN_NAME_SENDER = "Sender";
        public static final String COLUMN_NAME_MSG = "Message";
    }




}

