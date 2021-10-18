package dev.sid.attendr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AttendrMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        AttendrFirestoreConnector attendrFsCon = new AttendrFirestoreConnector(mFirestore);
        attendrFsCon.getDocument(count -> {
            Log.d(TAG, "DocumentSnapshot data: " + count);
            TextView countTxt = findViewById(R.id.count);
            countTxt.setText("" + count);
        });
    }
}