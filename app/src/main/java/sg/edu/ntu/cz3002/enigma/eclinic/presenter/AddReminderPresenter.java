package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.view.AddReminderView;

/**
 * Add reminder presenter
 */

public class AddReminderPresenter extends MvpBasePresenter<AddReminderView> {

    private static final String TAG = "AddReminderP";

    public void sendReservation(String doctor, String patient, String datetime) {
        Observable<ResponseBody> response = ApiManager.getInstance().sendReservation(doctor, patient, datetime);
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
                            getView().onReminderAddSuccess();
                        }
                    }
                });
    }
}
