package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.view.LoginView;

/**
 * Created by koAllen on 8/31/2016.
 */
public class LoginPresenter extends MvpBasePresenter<LoginView> {
    public static final String TAG = "LoginPresenter";

    public void authenticate(String username, String password) {
        Log.d(TAG, "Logging in");
        Observable<ResponseBody> response = ApiManager.getInstance().authenticate(username, password);
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
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
