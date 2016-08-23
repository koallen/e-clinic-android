package sg.edu.ntu.cz3002.enigma.eclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get device token for Firebase
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN", deviceToken);
    }
}
