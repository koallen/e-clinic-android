package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import sg.edu.ntu.cz3002.enigma.eclinic.view.SettingView;

/**
 * Created by HuaBa on 14/09/16.
 */
public class SettingPresenter extends MvpBasePresenter<SettingView> {
    private static final String TAG = "SettingPresenter";
    private Context _context;

    String uri = "@drawable/myresource";  // where myresource (without the extension) is the file

    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
    public SettingPresenter(Context context){
        _context = context;
    }
}
