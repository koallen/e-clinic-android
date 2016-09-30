package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.fragment.NotificationSettingFragment;

/**
 * NotificationSettingActivity
 */
public class NotificationSettingActivity extends AppCompatActivity {

    private static final String TAG = "NotifSettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationsetting);

        // UI initialization
        initializeToolbar();
        initializeNotificationSettingFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeToolbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar_notification_setting);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notification Settings");
    }

    private void initializeNotificationSettingFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.notification_frame, new NotificationSettingFragment())
                .commit();
    }
}
