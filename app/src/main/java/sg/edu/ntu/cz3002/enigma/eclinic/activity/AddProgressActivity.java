package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

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

public class AddProgressActivity extends MvpActivity<AddProgressView, AddProgressPresenter> implements AddProgressView, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private static final String TAG = "AddReminderActivity";

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

    @OnClick(R.id.add_reminder_button)
    public void onAddProgressButtonClicked(View view) {
        Log.d(TAG, "Adding progress");
        String datetime;
        String progress = _addProgressContent.getText().toString();
        presenter.sendProgress(_doctor, _patient, progress, datetime);
    }

    @Override
    public void onProgressAddSuccess() {
        Toast.makeText(this, "Added new reminder", Toast.LENGTH_SHORT).show();
        finish();
    }

    @NonNull
    @Override
    public AddProgressPresenter createPresenter() {
        return new AddProgressPresenter();
    }

    private void initializeToolbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar_addprogress);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Progress");
    }

}
