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
    private static String TAG = "FCMIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        saveCurrentToken(refreshedToken);
    }

    private void saveCurrentToken(String newToken) {
        SharedPreferences preferences = this.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Value.messageTokenPreferenceName, newToken);
        editor.apply();
        Log.d(TAG, "token saved to shared preference");
    }
}
