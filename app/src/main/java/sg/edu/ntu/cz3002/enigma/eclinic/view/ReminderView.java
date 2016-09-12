package sg.edu.ntu.cz3002.enigma.eclinic.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by SYY on 12/09/16.
 */
public interface ReminderView extends MvpView {
    void goToMainUi();
    void showError(String message);
}
