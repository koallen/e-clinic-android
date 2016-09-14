package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.ReminderPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ReminderView;

/**
 * Created by koAllen on 9/2/2016.
 */
public class ReminderFragment extends MvpFragment<ReminderView, ReminderPresenter> implements ReminderView {

    private static final String TAG = "ReminderFragment";

    private String[] reservationList;

    @BindView(R.id.reminders_swiperefresh) SwipeRefreshLayout _swipeRefreshLayout;
    @BindView(R.id.list) ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences preference = this.getActivity().getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
        final String patientName = preference.getString(Value.userNamePreferenceName, "no name");
        _swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "Refreshing");
                Toast.makeText(getActivity(), "Refreshing", Toast.LENGTH_SHORT).show();

                reservationList= presenter.getReservation(patientName);


                _swipeRefreshLayout.setRefreshing(false);

            }
        });


        String[] testArray = {"test1", "test2", "test3"};

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.reminder_list, testArray);
        listView.setAdapter(listAdapter);


        return view;
    }

    @Override
    public ReminderPresenter createPresenter() {return new ReminderPresenter();}

    public static ReminderFragment newInstance(int index) {
        ReminderFragment f = new ReminderFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }
}
