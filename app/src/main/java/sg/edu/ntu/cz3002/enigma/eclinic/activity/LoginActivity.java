package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.AppCompatButton;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.LoginPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.LoginView;

/**
 * Login activity
 */
public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.username_input_login) TextInputEditText _usernameText;
    @BindView(R.id.password_input_login) TextInputEditText _passwordText;
    @BindView(R.id.btn_login) AppCompatButton _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    ProgressDialog _progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @OnClick(R.id.btn_login)
    public void onLoginButtonClicked(View view) {
        Log.d(TAG, "Login clicked");

        // show a progress dialog
        _progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        _progressDialog.setIndeterminate(true);
        _progressDialog.setMessage("Authenticating...");
        _progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        presenter.authenticate(username, password);
    }

    @OnClick(R.id.link_signup)
    public void onSignupLinkClicked(View view) {
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _usernameText.setError("enter a valid email address");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void goToMainUi() {
        _progressDialog.dismiss();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showError(String message) {
        _progressDialog.dismiss();
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
