package sg.edu.ntu.cz3002.enigma.eclinic.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by koAllen on 9/4/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            // inform chat activity to update and display message
            String[] broadcastMessage = new String[3];
            broadcastMessage[0] = remoteMessage.getData().get("receiver");
            broadcastMessage[1] = remoteMessage.getData().get("sender");
            broadcastMessage[2] = remoteMessage.getData().get("message");
            // also include the receiving time
//            Date date = new Date();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM");
//            broadcastMessage[3] = format.format(date);

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
    }
}
