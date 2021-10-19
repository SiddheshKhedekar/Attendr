package dev.sid.attendr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
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
        TextView countTxt = findViewById(R.id.count);
        populateValue(attendrFsCon, countTxt);

        Button add = findViewById(R.id.add);
        add.setOnClickListener(view -> {
            int count = Integer.parseInt(countTxt.getText().toString());
            attendrFsCon.updateDocument(count);
            populateValue(attendrFsCon, countTxt);
        });
    }

    public void populateValue(AttendrFirestoreConnector attendrFsCon, TextView countTxt) {
        attendrFsCon.getDocument(count -> countTxt.setText(String.valueOf(count)));
    }
}