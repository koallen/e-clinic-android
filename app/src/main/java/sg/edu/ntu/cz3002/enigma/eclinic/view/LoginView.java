package sg.edu.ntu.cz3002.enigma.eclinic.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by koAllen on 8/31/2016.
 */
public interface LoginView extends MvpView {
    void goToMainUi();
    void showError(String message);
}
