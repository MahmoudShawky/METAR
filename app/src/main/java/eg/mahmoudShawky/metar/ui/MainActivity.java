package eg.mahmoudShawky.metar.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;
import eg.mahmoudShawky.metar.R;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}