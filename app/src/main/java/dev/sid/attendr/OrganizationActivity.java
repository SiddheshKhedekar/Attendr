package dev.sid.attendr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class OrganizationActivity extends AppCompatActivity {
    private static final String TAG = "AttendrOrganization";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = OrganizationActivity.this.getPreferences(Context.MODE_PRIVATE);
        String defaultOrganization = getResources().getString(R.string.saved_default_organization);
        String savedOrganization = sharedPref.getString(getString(R.string.saved_organization), defaultOrganization);
        if (!savedOrganization.equals(defaultOrganization)) {
            redirectToLogin();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        AttendrFirestoreConnector attendrFsCon = new AttendrFirestoreConnector(mFirestore);
        EditText org = findViewById(R.id.org);

        Button next = findViewById(R.id.next);
        next.setOnClickListener(view -> {
            String organization = org.getText().toString();
            attendrFsCon.checkIfOrganizationExists(attendrCallback -> {
                if (attendrCallback) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.saved_organization), organization);
                    editor.apply();
                    redirectToLogin();
                }
            },organization);
            Log.d(TAG, "Callback data exists");
        });
    }

    /** Called when the organization exist or is created */
    public void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

//    public void populateValue(AttendrFirestoreConnector attendrFsCon, EditText org) {
//        attendrFsCon.getDocument(count -> org.setText(String.valueOf(count)));
//    }
}