package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.activity.LoginActivity;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.DoctorSignupPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.SignupPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SignupView;

/**
 * Created by koallen on 24/9/16.
 */
public class DoctorSignupFragment extends MvpFragment<SignupView, DoctorSignupPresenter> implements SignupView {

    private static final String TAG = "DoctorSignupFragment";

    @BindView(R.id.username_input_signup_doctor) TextInputEditText _usernameText;
    @BindView(R.id.password_input_signup_doctor) TextInputEditText _passwordText;
    @BindView(R.id.btn_signup_doctor) AppCompatButton _signupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_signup_doctor)
    public void onSignupButtonClicked(View view){
        signup();
    }

    @OnClick(R.id.link_login_doctor)
    public void onLoginLinkClicked(View view){
        getActivity().finish();
    }

    @NonNull
    @Override
    public DoctorSignupPresenter createPresenter() {
        return new DoctorSignupPresenter();
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

//            if (name.isEmpty() || name.length() < 3) {
//                _nameText.setError("at least 3 characters");
//                valid = false;
//            } else {
//                _nameText.setError(null);
//            }
//
//            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                _emailText.setError("enter a valid email address");
//                valid = false;
//            } else {
//                _emailText.setError(null);
//            }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void onSignupFailed() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();

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

        // TODO: Implement your own signup logic here.

        presenter.signup(username, password);
        progressDialog.dismiss();

    }

    @Override
    public void goToLoginUi(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
