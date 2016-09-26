package sg.edu.ntu.cz3002.enigma.eclinic.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import sg.edu.ntu.cz3002.enigma.eclinic.model.Progress;

/**
 * Created by koallen on 27/9/16.
 */

public interface ProgressListView extends MvpView {
    void displayProgress(List<Progress> progressList);
    void showError(String message);
}
