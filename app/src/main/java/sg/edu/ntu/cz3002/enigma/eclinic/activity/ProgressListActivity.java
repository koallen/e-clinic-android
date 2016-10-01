package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.model.Progress;
import sg.edu.ntu.cz3002.enigma.eclinic.model.Reservation;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.ProgressListPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ProgressListView;

/**
 * Progress list activity
 */

public class ProgressListActivity extends MvpActivity<ProgressListView, ProgressListPresenter> implements ProgressListView {

    private static final String TAG = "ProgressLActivity";
    private String _patientName;
    private String _doctorName;

    @BindView(R.id.progress_swiperefresh)
    SwipeRefreshLayout _swipeRefreshLayout;
    @BindView(R.id.progress_list)
    ListView _listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_list);
        ButterKnife.bind(this);

        // get patient and doctor name
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _doctorName = extras.getString("doctor");
            _patientName = extras.getString("patient");
        }

        // UI initialization
        initializeToolbar();
        initializeSwipeRefreshLayout();
    }

    @NonNull
    @Override
    public ProgressListPresenter createPresenter() {
        return new ProgressListPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getProgress(_patientName, _doctorName);
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
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar_progress_list);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Progress List");
    }

    private void initializeSwipeRefreshLayout() {
        _swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "Refreshing");
                presenter.getProgress(_patientName, _doctorName);
                _swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void displayProgress(List<Progress> progressList) {
        Log.d(TAG, "displaying progress");
        ArrayList<HashMap<String, String>> _progressList;
        _progressList = new ArrayList<>();
        for (Progress progress : progressList){
            String content = progress.getContent();
            String time = progress.getTime();
            HashMap<String, String> progressMap = new HashMap<>();
            progressMap.put("content", content);
            progressMap.put("datetime", time);
            _progressList.add(progressMap);
        }

        ListAdapter adapter = new SimpleAdapter(this, _progressList, R.layout.progress_list, new String[]{"content", "datetime"}, new int[]{R.id.content, R.id.time});
        _listView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
