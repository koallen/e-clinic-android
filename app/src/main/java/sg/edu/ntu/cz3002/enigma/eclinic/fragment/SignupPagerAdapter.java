package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by koallen on 24/9/16.
 */
public class SignupPagerAdapter extends FragmentStatePagerAdapter {

    public SignupPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PatientSignupFragment patientTab = new PatientSignupFragment();
                return patientTab;
            case 1:
                DoctorSignupFragment doctorTab = new DoctorSignupFragment();
                return doctorTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
