package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import sg.edu.ntu.cz3002.enigma.eclinic.R;

/**
 * Created by HuaBa on 23/09/16.
 */
public class NotificationSettingActivity extends PreferenceActivity {

    private static final String TAG = "NotifSettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.notificationsetting_toolbar, root, false);
        root.addView(bar, 0); // insert at top
        setActionBar(bar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Notification Settings");
        addPreferencesFromResource(R.xml.preference);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "Menu item clicked");
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
