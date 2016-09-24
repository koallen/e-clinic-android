package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.model.User;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SignupView;

/**
 * Created by koallen on 24/9/16.
 */
public class DoctorSignupPresenter extends MvpBasePresenter<SignupView> {

    private static final String TAG = "DoctorSignupPresenter";
    private static final String HTTP_ERROR_MESSAGE = "Wrong credentials";
    private static final String NETWORK_ERROR_MESSAGE = "Network error";

    public void signup(String username, String password) {
        Log.d(TAG, "Connecting to remote server for sign up");
        Observable<User> response = ApiManager.getInstance().signup(username, password);
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<User>() {
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
                    public void onNext(User user) {
                        Log.d(TAG, "Sign up successful");
                        if (isViewAttached()) {
                            getView().showMessage("Sign up successful");
                            getView().goToLoginUi();
                        }
                    }
                });
    }

    @Override
    public void detachView(boolean retainInstance) {

    }
}
