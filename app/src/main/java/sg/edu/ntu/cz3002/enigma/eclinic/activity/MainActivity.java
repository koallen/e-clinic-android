package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check whether user logged in
        SharedPreferences preferences = this.getSharedPreferences(Value.preferenceFilename, this.MODE_PRIVATE);
        if (preferences.contains(Value.authTokenPreferenceName)) {
            // continue on MainActivity
            Log.d(TAG, "Auth token exists");
            setContentView(R.layout.activity_main);
        } else {
            // go to LoginActivity
            Log.d(TAG, "Auth token doesn't exist");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    // TODO: make sure it doesn't go back to LoginActivity
}
