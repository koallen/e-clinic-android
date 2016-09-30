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

import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.model.DbHelper;

/**
 * Firebase Messaging Service
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            // create a message object
            SharedPreferences preferences = this.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
            String receiver = preferences.getString(Value.userNamePreferenceName, "no name");
            String sender = remoteMessage.getData().get("from_user");
            String messageContent = remoteMessage.getData().get("message");

            // save the message to database
            DbHelper dbHelper = new DbHelper(this);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String datetime = simpleDateFormat.format(Calendar.getInstance().getTime());
            dbHelper.insertDb(receiver, sender, messageContent, datetime);

            // notify activity/fragment to update their UI
            String[] message = new String[2];
            message[0] = messageContent;
            message[1] = sender;
            broadcastMessage(message);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void broadcastMessage(String[] message) {
        Log.d(TAG, "Broadcasting message");
        Intent intent = new Intent("new-message");
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
