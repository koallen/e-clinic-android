package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.ReminderPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ReminderView;

/**
 * Created by koAllen on 9/2/2016.
 */
public class ReminderFragment extends MvpFragment<ReminderView, ReminderPresenter> implements ReminderView {

    private static final String TAG = "ReminderFragment";
    @BindView(R.id.reminders_swiperefresh) SwipeRefreshLayout _swipeRefreshLayout;
    @BindView(R.id.list) ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        ButterKnife.bind(this, view);
        _swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "Refreshing");
                Toast.makeText(getActivity(), "Refreshing", Toast.LENGTH_SHORT).show();

//                presenter.getReservation();

                _swipeRefreshLayout.setRefreshing(false);
            }
        });


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
