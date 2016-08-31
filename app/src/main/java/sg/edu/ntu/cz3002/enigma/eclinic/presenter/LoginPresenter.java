package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import sg.edu.ntu.cz3002.enigma.eclinic.view.LoginView;

/**
 * Created by koAllen on 8/31/2016.
 */
public class LoginPresenter extends MvpBasePresenter<LoginView> {
    public static final String TAG = "LoginPresenter";

    public void authenticate(String username, String password) {
        if (username.equals("ha") && password.equals("ha")) {
            Log.d(TAG, "credentials correct");
            if (isViewAttached()) {
                getView().goToMainUi();
            }
        } else {
            Log.d(TAG, "credentials wrong");
            if (isViewAttached()) {
                getView().showError("Wrong credentials");
            }
        }
    }

    public void detachView(boolean retainPresenterInstance){
        super.detachView(retainPresenterInstance);
        if (!retainPresenterInstance){
//            cancelGreetingTaskIfRunning();
        }
    }
}
