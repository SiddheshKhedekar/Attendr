package dev.sid.attendr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class OrganizationActivity extends AppCompatActivity {
    private static final String TAG = "AttendrOrganization";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        AttendrFirestoreConnector attendrFsCon = new AttendrFirestoreConnector(mFirestore);
        EditText org = findViewById(R.id.org);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        Button next = findViewById(R.id.next);
        next.setOnClickListener(view -> {
            String organization = org.getText().toString();
            attendrFsCon.checkIfOrganizationExists(attendrCallback -> {
                if (attendrCallback) {
                    sendMessage();
                }
            },organization);
            Log.d(TAG, "Callback data exists");
        });
    }

    /** Called when the user taps the Send button */
    public void sendMessage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

//    public void populateValue(AttendrFirestoreConnector attendrFsCon, EditText org) {
//        attendrFsCon.getDocument(count -> org.setText(String.valueOf(count)));
//    }
}