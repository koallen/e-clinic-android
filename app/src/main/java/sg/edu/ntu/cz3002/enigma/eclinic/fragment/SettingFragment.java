package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.os.Bundle;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import sg.edu.ntu.cz3002.enigma.eclinic.R;

/**
 * Created by koAllen on 9/2/2016.
 */
public class SettingFragment extends PreferenceFragmentCompat {
    private static final String TAG = "ChatFragment";

    @Override
    public void onCreatePreferencesFix(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    public static SettingFragment newInstance(int index) {
        SettingFragment f = new SettingFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }
}
