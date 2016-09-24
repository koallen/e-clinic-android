package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.json.JSONObject;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.model.AuthToken;
import sg.edu.ntu.cz3002.enigma.eclinic.model.Reservation;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ReminderView;

/**
 * Created by SYY on 12/09/16.
 */
public class ReminderPresenter extends MvpBasePresenter<ReminderView>{

    private static final String TAG = "ReminderPresenter";
    private static final String HTTP_ERROR_MESSAGE = "Wrong credentials";
    private static final String NETWORK_ERROR_MESSAGE = "Network error";


    public ReminderPresenter() {

    }

    public void getReservation(String patientName){
        Log.d(TAG, "Connecting to remote server for requesting reservation info");
        Observable<List<Reservation>> response = ApiManager.getInstance().getReservation(patientName);
        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Reservation>>() {
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
                    public void onNext(List<Reservation> reservations) {
                        if (isViewAttached()) {
                            Log.d(TAG, "Getting data successful");
                            Log.d(TAG, reservations.toString());
                            getView().showReminders(reservations);
                        }
                    }
                });
//        return new String[]{"1", "2", "3"};
    }



    public void detachView(boolean retainPresenterInstance){
        super.detachView(retainPresenterInstance);
        if (!retainPresenterInstance){
//            cancelGreetingTaskIfRunning();
        }
    }


}
