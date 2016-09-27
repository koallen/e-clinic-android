package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

import sg.edu.ntu.cz3002.enigma.eclinic.R;

/**
 * Created by koallen on 28/9/16.
 */

public class TimePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), R.style.Picker, (TimePickerDialog.OnTimeSetListener)getActivity(), hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
}
