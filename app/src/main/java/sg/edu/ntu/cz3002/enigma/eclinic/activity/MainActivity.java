package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sg.edu.ntu.cz3002.enigma.eclinic.R;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_main);
    }
}
