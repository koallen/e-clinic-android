package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import sg.edu.ntu.cz3002.enigma.eclinic.view.UserProfileView;

/**
 * Created by HuaBa on 17/09/16.
 */
public class UserProfilePresenter extends MvpBasePresenter<UserProfileView> {
    private static final String TAG="UserProfilePresenter";
    private Context _context;

    public UserProfilePresenter (Context context) {
        _context=context;
    }
}
