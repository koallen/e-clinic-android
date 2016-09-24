package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.ntu.cz3002.enigma.eclinic.R;

/**
 * Created by koallen on 24/9/16.
 */
public class PatientSignupFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patient_signup, container, false);
    }
}
