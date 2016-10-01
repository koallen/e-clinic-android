package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.model.Patient;
import sg.edu.ntu.cz3002.enigma.eclinic.model.User;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SignupView;

/**
 * Patient signup presenter
 */

public class PatientSignupPresenter extends MvpBasePresenter<SignupView> {

    private static final String TAG = "PatientSignupPresenter";
    private static final String HTTP_ERROR_MESSAGE = "Wrong credentials";
    private static final String NETWORK_ERROR_MESSAGE = "Network error";

    public void signup(final String username, final String password, final String gender, final int age) {
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
                        registerAsPatient(username, gender, age);
                    }
                });
    }

    private void registerAsPatient(String username, String gender, int age) {
        Log.d(TAG, "signing up as patient");
        Observable<Patient> response = ApiManager.getInstance().registerAsPatient(username, gender, age);
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Patient>() {
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
                    public void onNext(Patient patient) {
                        Log.d(TAG, "Patient created");
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
