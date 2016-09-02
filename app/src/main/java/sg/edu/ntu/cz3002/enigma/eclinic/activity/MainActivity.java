package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.fragment.ChatFragment;
import sg.edu.ntu.cz3002.enigma.eclinic.fragment.ReminderFragment;
import sg.edu.ntu.cz3002.enigma.eclinic.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.bottomBar) BottomBar _bottombar;
    FragNavController _fragNavController;

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
            setFragments();
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
                        _fragNavController.switchTab(FragNavController.TAB1);
                        break;
                    case R.id.chats_button:
                        _fragNavController.switchTab(FragNavController.TAB2);
                        break;
                    case R.id.settings_button:
                        _fragNavController.switchTab(FragNavController.TAB3);
                        break;
                }
            }
        });
    }

    public void setFragments() {
        List<Fragment> fragments = new ArrayList<>(3);

        fragments.add(new ReminderFragment());
        fragments.add(new ChatFragment());
        fragments.add(new SettingFragment());

        _fragNavController = new FragNavController(getSupportFragmentManager(), R.id.contentContainer, fragments);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}