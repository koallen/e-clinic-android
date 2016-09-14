package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.ChatPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.SettingPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SettingView;

/**
 * Created by koAllen on 9/2/2016.
 */
public class SettingFragment extends MvpFragment<SettingView, SettingPresenter> implements SettingView {
    private static final String TAG = "ChatFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @NonNull
    @Override
    public SettingPresenter createPresenter(){
        return new SettingPresenter(this.getContext());
    }

    public static SettingFragment newInstance(int index) {
        SettingFragment f = new SettingFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }
//    @Override
//    public void onCreatePreferencesFix(Bundle savedInstanceState, String rootKey) {
//        setPreferencesFromResource(R.xml.preferences, rootKey);
//    }
//
//    public static SettingFragment newInstance(int index) {
//        SettingFragment f = new SettingFragment();
//
//        // Supply index input as an argument.
//        Bundle args = new Bundle();
//        args.putInt("index", index);
//        f.setArguments(args);
//
//        return f;
//    }
}
