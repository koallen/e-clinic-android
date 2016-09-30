package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.content.Context;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sg.edu.ntu.cz3002.enigma.eclinic.model.ApiManager;
import sg.edu.ntu.cz3002.enigma.eclinic.model.Message;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ChatView;

/**
 * Created by ZWL on 12/9/16.
 */
public class ChatPresenter extends MvpBasePresenter<ChatView>{

    private static final String TAG = "ChatPresenter";
    private static final String HTTP_ERROR_MESSAGE = "Wrong credentials";
    private static final String NETWORK_ERROR_MESSAGE = "Network error";

    public void detachView(boolean retainPresenterInstance){
        super.detachView(retainPresenterInstance);
        if (!retainPresenterInstance){
//            cancelGreetingTaskIfRunning();
        }
    }

    public void send(String message, String from_user, String to_user){
        Observable<ResponseBody> response = ApiManager.getInstance().sendMessage(message, to_user, from_user);
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

                    }
                });
    }
}
