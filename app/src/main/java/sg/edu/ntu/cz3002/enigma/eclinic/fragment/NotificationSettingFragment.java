package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import sg.edu.ntu.cz3002.enigma.eclinic.R;

/**
 * Created by koallen on 25/9/16.
 */

public class NotificationSettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference);
    }
}
