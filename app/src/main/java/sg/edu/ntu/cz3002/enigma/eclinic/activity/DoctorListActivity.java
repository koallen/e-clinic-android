package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.model.Doctor;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.DoctorListPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.DoctorListView;

/**
 * Doctor list activity
 */

public class DoctorListActivity extends MvpActivity<DoctorListView, DoctorListPresenter> implements DoctorListView {
    private static final String TAG = "DoctorListActivity";

    @BindView(R.id.doctor_list)
    ListView listView;
    @BindView(R.id.doctor_swiperefresh)
    SwipeRefreshLayout _swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        ButterKnife.bind(this);

        initializeToolbar();
        initializeSwipeRefreshLayout();
    }

    private void initializeSwipeRefreshLayout() {
        _swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "Refreshing");
                presenter.getDoctorList();
                _swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @NonNull
    @Override
    public DoctorListPresenter createPresenter() {
        return new DoctorListPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getDoctorList();
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

    @Override
    public void showDoctorList(List<Doctor> doctorList) {
        Log.d(TAG, "displaying doctors");
        ArrayList<HashMap<String, String>> _doctorList;
        _doctorList = new ArrayList<>();
        for (Doctor doctor : doctorList){
            String name = doctor.getUser();
            String clinic = doctor.getClinic();
            HashMap<String, String> doctorMap = new HashMap<>();
            doctorMap.put("name", name);
            doctorMap.put("clinic", clinic);
            _doctorList.add(doctorMap);
        }

        ListAdapter adapter = new SimpleAdapter(this, _doctorList, R.layout.doctor_list, new String[]{"name", "clinic"}, new int[]{R.id.name, R.id.clinic});
        listView.setAdapter(adapter);
    }


    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void initializeToolbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar_doctor_list);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Doctor List");
    }
}
