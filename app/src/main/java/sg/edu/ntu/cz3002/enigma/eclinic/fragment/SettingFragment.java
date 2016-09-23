package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.activity.LoginActivity;
import sg.edu.ntu.cz3002.enigma.eclinic.activity.NotificationSettingActivity;
import sg.edu.ntu.cz3002.enigma.eclinic.activity.UserProfileActivity;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.SettingPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SettingView;

/**
 * Created by koAllen on 9/2/2016.
 */
public class SettingFragment extends MvpFragment<SettingView, SettingPresenter> implements SettingView {
    private static final String TAG = "SettingFragment";
    // This is the Adapter being used to display the list's data
    ArrayAdapter mAdapter;
    @BindView(R.id.listview) ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences prefs = getActivity().getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE); // here you get your prefrences by either of two methods
        String username = prefs.getString(Value.userNamePreferenceName, "");
        SettingItem[] fromColumns = {
                new SettingItem(R.drawable.ic_account_circle_black_24dp, username),
                new SettingItem(R.drawable.ic_settings_black_24dp, "Notification Setting"),
                new SettingItem(R.drawable.ic_exit_to_app_black_24dp,"Log Out")
        };
        mAdapter = new SettingArrayAdapter(this.getActivity(), R.layout.setting_list, fromColumns);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), NotificationSettingActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        presenter.clear();
                        Toast.makeText(getActivity(), "Sign Out Successful", Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent2);
                        getActivity().finish();
                        break;
                    default:
                        Toast.makeText(getActivity(), "haha", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        return view;
    }
    @NonNull
    @Override
    public SettingPresenter createPresenter(){
        return new SettingPresenter(this.getActivity());
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
