package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.edu.ntu.cz3002.enigma.eclinic.R;

/**
 * Doctor detail activity
 */

public class DoctorDetailActivity extends AppCompatActivity {

    private static final String TAG = "DoctorDetailActivity";

    @BindView(R.id.doctor_detail_name)
    TextView _name;
    @BindView(R.id.doctor_detail_gender)
    TextView _gender;
    @BindView(R.id.doctor_detail_clinic)
    TextView _clinic;
    @BindView(R.id.doctor_detail_description)
    TextView _description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctordetail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String gender = extras.getString("gender");
            String clinic = extras.getString("clinic");
            String description = extras.getString("description");
            _name.setText(name);
            _gender.setText(gender);
            _clinic.setText(clinic);
            _description.setText(description);
        }

        initializeToolbar();
    }

    private void initializeToolbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar_doctor_detail);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Doctor Detail");
    }

    @OnClick(R.id.doctor_detail_talk_button)
    public void onTalkButtonClicked(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("sender", _name.getText().toString());
        startActivity(intent);
        // TODO: make the back button behave the same way as toolbar "up" botton
        finish();
    }
}
