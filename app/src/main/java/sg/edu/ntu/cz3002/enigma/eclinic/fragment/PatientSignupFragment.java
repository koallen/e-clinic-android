package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.activity.LoginActivity;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.PatientSignupPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SignupView;

/**
 * Patient signup fragment
 */
public class PatientSignupFragment extends MvpFragment<SignupView, PatientSignupPresenter> implements SignupView {

    private static final String TAG = "PatientSignupFragment";

    @BindView(R.id.username_input_signup_patient)
    TextInputEditText _usernameText;
    @BindView(R.id.password_input_signup_patient)
    TextInputEditText _passwordText;
    @BindView(R.id.age_input_signup_patient)
    TextInputEditText _ageText;
    @BindView(R.id.btn_signup_patient)
    AppCompatButton _signupButton;

    private String _gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_signup_patient)
    public void onSignupButtonClicked(View view){
        signup();
    }

    @OnClick(R.id.link_login_patient)
    public void onLoginLinkClicked(View view){
        getActivity().finish();
    }

    public void onGenderRadioButtonClicked(View view) {
        Log.d(TAG, "Radio button clicked");
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_male_patient:
                if (checked)
                    _gender = "M";
                    break;
            case R.id.radio_female_patient:
                if (checked)
                    _gender = "F";
                    break;
        }
    }

    @NonNull
    @Override
    public PatientSignupPresenter createPresenter() {
        return new PatientSignupPresenter();
    }

    public boolean validate() {
        String password = _passwordText.getText().toString();
        int age;

        // validate password
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            Toast.makeText(getActivity(), "between 4 and 10 alphanumeric characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        // validate age
        try {
            age = Integer.parseInt(_ageText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "please input your age", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (age < 1 && age > 200) {
            Toast.makeText(getActivity(), "age should be between 1 and 200", Toast.LENGTH_SHORT).show();
            return false;
        }

        // validate gender
        if (_gender == null) {
            Toast.makeText(getActivity(), "gender not selected", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void onSignupFailed() {
        _signupButton.setEnabled(true);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String gender = _gender;
        int age = Integer.parseInt(_ageText.getText().toString());

        presenter.signup(username, password, gender, age);
        progressDialog.dismiss();

    }

    @Override
    public void goToLoginUi() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
