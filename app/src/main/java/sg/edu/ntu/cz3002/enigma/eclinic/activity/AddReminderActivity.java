package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.fragment.DatePickerFragment;
import sg.edu.ntu.cz3002.enigma.eclinic.fragment.TimePickerFragment;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.AddReminderPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.AddReminderView;

/**
 * Add reminder activity
 */

public class AddReminderActivity extends MvpActivity<AddReminderView, AddReminderPresenter> implements AddReminderView, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddReminderActivity";

    @BindView(R.id.add_reminder_date)
    TextView _dateTextView;
    @BindView(R.id.add_reminder_time)
    TextView _timeTextView;

    private String _doctor;
    private String _patient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);
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
    public void onAddReminderButtonClicked(View view) {
        Log.d(TAG, "Adding reminder");
        String datetime = _dateTextView.getText().toString() + " " + _timeTextView.getText().toString();
        presenter.sendReservation(_doctor, _patient, datetime);
    }

    @OnClick(R.id.add_reminder_date)
    public void onDateTextClicked(View view) {
        Log.d(TAG, "date text clicked");
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @OnClick(R.id.add_reminder_time)
    public void onTimeTextClicked(View view) {
        Log.d(TAG, "time text clicked");
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @NonNull
    @Override
    public AddReminderPresenter createPresenter() {
        return new AddReminderPresenter();
    }

    @Override
    public void onReminderAddSuccess() {
        Toast.makeText(this, "Added new reminder", Toast.LENGTH_SHORT).show();
        finish();
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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "setting new date: " + year + " " + (month + 1) + " " + dayOfMonth);
        SimpleDateFormat simpleDateFormatOldFormat = new SimpleDateFormat("yyyy-M-d");
        SimpleDateFormat simpleDateFormatNewFormat = new SimpleDateFormat("yyyy-MM-dd");
        String oldFormatDateTime = year + "-" + (month + 1) + "-" + dayOfMonth;
        Date date;
        try {
            date = simpleDateFormatOldFormat.parse(oldFormatDateTime);
            String newFormatDateTime = simpleDateFormatNewFormat.format(date);
            _dateTextView.setText(newFormatDateTime);
        } catch (ParseException e) { }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        _timeTextView.setText(hourOfDay + ":" + minute);
    }

    private void initializeToolbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar_add_reminder);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Reminder");
    }
}
