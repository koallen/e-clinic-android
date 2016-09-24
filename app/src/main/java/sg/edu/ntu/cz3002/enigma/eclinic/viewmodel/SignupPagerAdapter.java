package sg.edu.ntu.cz3002.enigma.eclinic.viewmodel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sg.edu.ntu.cz3002.enigma.eclinic.fragment.DoctorSignupFragment;
import sg.edu.ntu.cz3002.enigma.eclinic.fragment.PatientSignupFragment;

/**
 * Signup page adapter
 */
public class SignupPagerAdapter extends FragmentStatePagerAdapter {

    public SignupPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PatientSignupFragment();
            case 1:
                return new DoctorSignupFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
