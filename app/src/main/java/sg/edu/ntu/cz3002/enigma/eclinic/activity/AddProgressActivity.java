package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Override
    public void onProgressAddSuccess() {
        Toast.makeText(this, "Added new reminder", Toast.LENGTH_SHORT).show();
        finish();
    }

}
