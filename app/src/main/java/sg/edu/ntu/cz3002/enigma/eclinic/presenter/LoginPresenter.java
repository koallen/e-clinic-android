package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.model.AuthToken;
import sg.edu.ntu.cz3002.enigma.eclinic.view.LoginView;

/**
 * Login presenter
 */
public class LoginPresenter extends MvpBasePresenter<LoginView> {
    private static final String TAG = "LoginPresenter";
    private Context _context;

    public LoginPresenter(Context context) {
        _context = context;
    }

    public void authenticate(String username, String password) {
        Log.d(TAG, "Logging in");
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
                        // TODO: handle HTTP 400 BAD REQUEST
                    }

                    @Override
                    public void onNext(AuthToken authToken) {
                        // save the auth token to shared preferences
                        SharedPreferences preferences = _context.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Value.authTokenPreferenceName, authToken.getToken());
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
