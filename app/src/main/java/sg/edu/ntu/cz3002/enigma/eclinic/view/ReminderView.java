package sg.edu.ntu.cz3002.enigma.eclinic.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import sg.edu.ntu.cz3002.enigma.eclinic.model.Reservation;

/**
 * Created by SYY on 12/09/16.
 */
public interface ReminderView extends MvpView {
//    void goToMainUi();
    void showError(String message);
    void showReminders(List<Reservation> reservations);

}
