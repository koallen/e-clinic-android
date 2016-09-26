package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.model.Progress;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ProgressListView;

/**
 * Created by koallen on 27/9/16.
 */

public class ProgressListPresenter extends MvpBasePresenter<ProgressListView> {

    private static final String TAG = "ReminderPresenter";
    private static final String HTTP_ERROR_MESSAGE = "Wrong credentials";
    private static final String NETWORK_ERROR_MESSAGE = "Network error";

    public void getProgress(String patientName, String doctorName) {
        Observable<List<Progress>> response = ApiManager.getInstance().getProgress(patientName, doctorName);
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Progress>>() {
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
                    public void onNext(List<Progress> progresses) {
                        Log.d(TAG, progresses.toString());
                        if (isViewAttached()) {
                            getView().displayProgress(progresses);
                        }
                    }
                });
    }
}
