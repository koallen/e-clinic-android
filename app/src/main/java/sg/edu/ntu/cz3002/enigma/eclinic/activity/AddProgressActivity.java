package sg.edu.ntu.cz3002.enigma.eclinic.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.AddProgressPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.AddReminderPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.AddProgressView;
import sg.edu.ntu.cz3002.enigma.eclinic.view.AddReminderView;

/**
 * Created by Allen on 2016/10/1.
 */

public class AddProgressActivity extends MvpActivity<AddProgressView, AddProgressPresenter> implements AddProgressView{
    private static final String TAG = "AddProgressActivity";

    @BindView(R.id.add_progress_content)
    TextView _addProgressContent;

    private String _doctor;
    private String _patient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprogress);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            _doctor = bundle.getString("doctor");
            _patient = bundle.getString("patient");
        }

        // UI initialization
        initializeToolbar();
    }

    @OnClick(R.id.add_progress_button)
    public void onAddProgressButtonClicked(View view) {
        Log.d(TAG, "Adding progress");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String datetime = df.format(c.getTime());
        String progress = _addProgressContent.getText().toString();
        Log.d(TAG, datetime);
        presenter.sendProgress(_doctor, _patient, progress, datetime);
    }

    @Override
    public void onProgressAddSuccess() {
        Toast.makeText(this, "Added new progress", Toast.LENGTH_SHORT).show();
        finish();
    }

    @NonNull
    @Override
    public AddProgressPresenter createPresenter() {
        return new AddProgressPresenter();
    }

    private void initializeToolbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar_add_progress);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Progress");
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

}
