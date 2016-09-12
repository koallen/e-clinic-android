package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.SignupPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SignupView;


/**
 * Signup activity
 */
    public class SignupActivity extends MvpActivity<SignupView, SignupPresenter> implements SignupView {
        private static final String TAG = "SignupActivity";

        @BindView(R.id.username_input_signup) TextInputEditText _usernameText;
        @BindView(R.id.password_input_signup) TextInputEditText _passwordText;
        @BindView(R.id.btn_signup)
        AppCompatButton _signupButton;
        @BindView(R.id.link_login) TextView _loginLink;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
            ButterKnife.bind(this);
        }

        @OnClick(R.id.btn_signup)
        public void onSignupButtonClicked(View view){
            signup();
        }

        @OnClick(R.id.link_login)
        public void onLoginLinkClicked(View view){
            finish();
        }
        @NonNull
        @Override
        public SignupPresenter createPresenter() {
            return new SignupPresenter(this);
        }

        public void signup() {
            Log.d(TAG, "Signup");

            if (!validate()) {
                onSignupFailed();
                return;
            }

            _signupButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();

            String username = _usernameText.getText().toString();
            String password = _passwordText.getText().toString();

            // TODO: Implement your own signup logic here.

            presenter.signup(username, password);
            progressDialog.dismiss();

        }


        public void onSignupSuccess() {
            _signupButton.setEnabled(true);
            setResult(RESULT_OK, null);
            finish();
        }

        public void onSignupFailed() {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

            _signupButton.setEnabled(true);
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

        @Override
        public void goToLoginUi(){
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        @Override
        public void showError(String message) {
            Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
        }
        @Override
        public void showMessage(String message) {
            Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

