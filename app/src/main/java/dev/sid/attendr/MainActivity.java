package dev.sid.attendr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AttendrMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        AttendrFirestoreConnector attendrFsCon = new AttendrFirestoreConnector(mFirestore);
        EditText org = findViewById(R.id.org);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        Button next = findViewById(R.id.next);
        next.setOnClickListener(view -> {
            String organization = org.getText().toString();
            attendrFsCon.checkDocument(attendrCallback -> Snackbar.make(constraintLayout, String.valueOf(attendrCallback), Snackbar.LENGTH_LONG).show(),organization);
            Log.d(TAG, "Callback data exists");
        });
    }

//    public void populateValue(AttendrFirestoreConnector attendrFsCon, EditText org) {
//        attendrFsCon.getDocument(count -> org.setText(String.valueOf(count)));
//    }
}