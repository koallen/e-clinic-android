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
import sg.edu.ntu.cz3002.enigma.eclinic.model.Doctor;
import sg.edu.ntu.cz3002.enigma.eclinic.model.User;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SignupView;

/**
 * Doctor signup presenter
 */
public class DoctorSignupPresenter extends MvpBasePresenter<SignupView> {

    private static final String TAG = "DoctorSignupPresenter";
    private static final String HTTP_ERROR_MESSAGE = "Wrong credentials";
    private static final String NETWORK_ERROR_MESSAGE = "Network error";

    public void signup(final String username, final String password, final String gender, final String clinic, final String description) {
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
                        registerAsDoctor(username, gender, clinic, description);
                    }
                });
    }

    private void registerAsDoctor(String username, String gender, String clinic, String description) {
        Log.d(TAG, "registering as a doctor");
        Observable<Doctor> response = ApiManager.getInstance().registerAsDoctor(username, gender, clinic, description);
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Doctor>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Doctor doctor) {
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
