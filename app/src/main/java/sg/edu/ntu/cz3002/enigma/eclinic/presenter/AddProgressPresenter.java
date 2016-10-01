package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.view.AddProgressView;

/**
 * Created by Allen on 2016/10/1.
 */

public class AddProgressPresenter extends MvpBasePresenter<AddProgressView> {
    private static final String TAG = "AddReminderP";

    public void sendProgress(String doctor, String patient, String progress, String datetime) {
        Observable<ResponseBody> response = ApiManager.getInstance().sendProgress(doctor, patient, progress, datetime);
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
                            getView().onProgressAddSuccess();
                        }
                    }
                });
    }
}
