package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

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
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.SettingPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SettingView;

/**
 * Created by koAllen on 9/2/2016.
 */
public class SettingFragment extends MvpFragment<SettingView, SettingPresenter> implements SettingView {
    private static final String TAG = "ChatFragment";
    // This is the Adapter being used to display the list's data
    ArrayAdapter mAdapter;

    // These are the Contacts rows that we will retrieve
    @BindView(R.id.listview) ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        SettingItem[] fromColumns = {
                new SettingItem(R.drawable.ic_account_circle_black_24dp,"User Name"),
                new SettingItem(R.drawable.ic_exit_to_app_black_24dp,"Log Out")
        };
        mAdapter = new SettingArrayAdapter(this.getActivity(), R.layout.setting_list, fromColumns);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // your code
                // Toast.makeText(context,temparr.get(position),Toast.LENGTH_SHORT).show();
                switch(position){
                    case 0:
                        Toast.makeText(getActivity(), "0", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "1", Toast.LENGTH_LONG).show();
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
