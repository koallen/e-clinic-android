package sg.edu.ntu.cz3002.enigma.eclinic.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import sg.edu.ntu.cz3002.enigma.eclinic.model.Doctor;


/**
 * Created by Allen on 2016/10/1.
 */

public interface DoctorListView extends MvpView {

    void showError(String message);
    void showDoctorList(List<Doctor> doctors);

}
