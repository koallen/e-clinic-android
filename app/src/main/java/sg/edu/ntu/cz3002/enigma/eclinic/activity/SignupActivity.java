package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.fragment.DoctorSignupFragment;
import sg.edu.ntu.cz3002.enigma.eclinic.fragment.PatientSignupFragment;
import sg.edu.ntu.cz3002.enigma.eclinic.viewmodel.SignupPagerAdapter;


/**
 * Signup activity
 */
public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    @BindView(R.id.pager)
    ViewPager _viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        // set up tab switching
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Patient"));
        tabLayout.addTab(tabLayout.newTab().setText("Doctor"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter adapter = new SignupPagerAdapter
                (getSupportFragmentManager());
        _viewPager.setAdapter(adapter);
        _viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                _viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void onGenderRadioButtonClicked(View view) {
        Fragment fragment = ((SignupPagerAdapter)_viewPager.getAdapter()).getCurrentFragment();
        if (fragment instanceof PatientSignupFragment) {
            ((PatientSignupFragment) fragment).onGenderRadioButtonClicked(view);
        } else if (fragment instanceof DoctorSignupFragment) {
            ((DoctorSignupFragment) fragment).onGenderRadioButtonClicked(view);
        }
    }
}

