package sg.edu.ntu.cz3002.enigma.eclinic.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by HuaBa on 12/09/16.
 */
public interface SignupView extends MvpView {
    void goToLoginUi();
    void showMessage(String message);
    void showError(String message);
}
