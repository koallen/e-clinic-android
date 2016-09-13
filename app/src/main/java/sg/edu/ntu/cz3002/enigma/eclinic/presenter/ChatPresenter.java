package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import sg.edu.ntu.cz3002.enigma.eclinic.view.ChatView;

/**
 * Created by ZWL on 12/9/16.
 */
public class ChatPresenter extends MvpBasePresenter<ChatView>{
    private Context _context;

    public ChatPresenter(Context context){
        _context = context;
    }



    public void detachView(boolean retainPresenterInstance){
        super.detachView(retainPresenterInstance);
        if (!retainPresenterInstance){
//            cancelGreetingTaskIfRunning();
        }
    }
}
