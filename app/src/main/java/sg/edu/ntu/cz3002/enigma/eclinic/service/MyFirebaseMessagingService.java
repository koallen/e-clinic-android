package sg.edu.ntu.cz3002.enigma.eclinic.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.model.DbHelper;

/**
 * Created by koAllen on 9/4/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            // inform chat activity to update and display message
            String[] broadcastMessage = new String[2];
//            broadcastMessage[0] = remoteMessage.getData().get("receiver");
            broadcastMessage[0] = remoteMessage.getData().get("from_user");
            broadcastMessage[1] = remoteMessage.getData().get("message");
            System.out.println(broadcastMessage[0] + broadcastMessage[1]);

            // save the message to database
            DbHelper dbHelper = new DbHelper(this);
            SharedPreferences preferences = this.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
            String user = preferences.getString(Value.userNamePreferenceName, "no name");
            dbHelper.insertDb(user, broadcastMessage[0], broadcastMessage[1], simpleDateFormat.format(Calendar.getInstance().getTime()));
            Log.d(TAG, "inserted to database " + simpleDateFormat.format(Calendar.getInstance().getTime()));

            // notify activity/fragment to update their UI
            broadcastMessage(broadcastMessage);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void broadcastMessage(String[] remoteMessage) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("new-message");
        intent.putExtra("message",remoteMessage);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//        sendOrderedBroadcast(intent,null); // set permission to null : no permission is required
    }
}
