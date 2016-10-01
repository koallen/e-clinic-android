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
import sg.edu.ntu.cz3002.enigma.eclinic.model.Doctor;
import sg.edu.ntu.cz3002.enigma.eclinic.model.Reservation;
import sg.edu.ntu.cz3002.enigma.eclinic.view.DoctorListView;

/**
 * Created by Allen on 2016/10/1.
 */

public class DoctorListPresenter extends MvpBasePresenter<DoctorListView> {

    private static final String TAG = "DoctorListPresenter";
    private static final String HTTP_ERROR_MESSAGE = "Wrong credentials";
    private static final String NETWORK_ERROR_MESSAGE = "Network error";

    public void getDoctorList() {
        Log.d(TAG, "Connecting to remote server for requesting reservation info");
        Observable<List<Doctor>> response = ApiManager.getInstance().getDoctorList();
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
                        if (isViewAttached()) {
                            Log.d(TAG, "Getting data successful");
                            Log.d(TAG, doctors.toString());
                            getView().showDoctorList(doctors);
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
