package sg.edu.ntu.cz3002.enigma.eclinic.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.view.SettingView;

/**
 * Created by HuaBa on 14/09/16.
 */
public class SettingPresenter extends MvpBasePresenter<SettingView> {
    private static final String TAG = "SettingPresenter";
    private Context _context;

    public SettingPresenter(Context context){
        _context = context;
    }
    public void clear(){
        SharedPreferences prefs = _context.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE); // here you get your prefrences by either of two methods
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
