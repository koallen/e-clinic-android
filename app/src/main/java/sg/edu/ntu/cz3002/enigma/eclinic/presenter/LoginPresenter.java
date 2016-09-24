package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.model.AuthToken;
import sg.edu.ntu.cz3002.enigma.eclinic.model.Doctor;
import sg.edu.ntu.cz3002.enigma.eclinic.view.LoginView;

/**
 * Login presenter
 */
public class LoginPresenter extends MvpBasePresenter<LoginView> {

    private static final String TAG = "LoginPresenter";
    private static final String HTTP_ERROR_MESSAGE = "Wrong credentials";
    private static final String NETWORK_ERROR_MESSAGE = "Network error";
    private Context _context;

    public LoginPresenter(Context context) {
        _context = context;
    }

    public void authenticate(final String username, final String password) {
        Log.d(TAG, "Connecting to remote server for authentication");
        Observable<AuthToken> response = ApiManager.getInstance().authenticate(username, password);
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AuthToken>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            Log.d(TAG, HTTP_ERROR_MESSAGE);
                            if (isViewAttached()) {
                                getView().showError(HTTP_ERROR_MESSAGE);
                            }
                        } else {
                            Log.d(TAG, NETWORK_ERROR_MESSAGE);
                            if (isViewAttached()) {
                                getView().showError(NETWORK_ERROR_MESSAGE);
                            }
                        }

                    }

                    @Override
                    public void onNext(AuthToken authToken) {
                        Log.d(TAG, "Authentication successful");
                        // save the auth token to shared preferences
                        SharedPreferences preferences = _context.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Value.authTokenPreferenceName, authToken.getToken());
                        editor.putString(Value.userNamePreferenceName, username);
                        editor.apply();

                        testIdentity(username);
                    }
                });
    }

    private void testIdentity(final String username) {
        Observable<List<Doctor>> response = ApiManager.getInstance().testIdentity(username);
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Doctor>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            Log.d(TAG, HTTP_ERROR_MESSAGE);
                            if (isViewAttached()) {
                                getView().showError(HTTP_ERROR_MESSAGE);
                            }
                        } else {
                            Log.d(TAG, NETWORK_ERROR_MESSAGE);
                            if (isViewAttached()) {
                                getView().showError(NETWORK_ERROR_MESSAGE);
                            }
                        }
                    }

                    @Override
                    public void onNext(List<Doctor> doctors) {
                        SharedPreferences preferences = _context.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        if (doctors.isEmpty()) {
                            Log.d(TAG, "Patient logging in");
                            editor.putString(Value.userTypePreferenceName, "patient");
                        } else {
                            Log.d(TAG, "Doctor logging in");
                            editor.putString(Value.userTypePreferenceName, "doctor");
                        }
                        editor.apply();

                        if (isViewAttached()) {
                            getView().goToMainUi();
                        }
                    }
                });
    }

    public void detachView(boolean retainPresenterInstance){
        super.detachView(retainPresenterInstance);
        if (!retainPresenterInstance){
//            cancelGreetingTaskIfRunning();
        }
    }

}
