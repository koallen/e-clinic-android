package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.bottomBar) BottomBar _bottombar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check whether user logged in
        SharedPreferences preferences = this.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
        if (preferences.contains(Value.authTokenPreferenceName)) {
            // continue on MainActivity
            Log.d(TAG, "Auth token exists");
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            // set up the UI components
            setBottomBar();
        } else {
            // go to LoginActivity
            Log.d(TAG, "Auth token doesn't exist");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void setBottomBar() {
        _bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.reminders_button:
                        Toast.makeText(MainActivity.this, "Reminders", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.chats_button:
                        Toast.makeText(MainActivity.this, "Chats", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings_button:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}