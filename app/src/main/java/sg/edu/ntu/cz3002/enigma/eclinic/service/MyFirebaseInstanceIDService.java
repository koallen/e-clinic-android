package sg.edu.ntu.cz3002.enigma.eclinic.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import okhttp3.ResponseBody;
import rx.Observable;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;

/**
 * Created by koAllen on 24/8/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static String TAG = "MyFirebaseInstanceIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        SharedPreferences preferences = this.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
        if (preferences.contains(Value.userNamePreferenceName)) {
            String username = preferences.getString(Value.userNamePreferenceName, "none");
            Observable<ResponseBody> response = ApiManager.getInstance().sendMessageToken(username, refreshedToken);
            // TODO: send token to server
        }
    }
}
