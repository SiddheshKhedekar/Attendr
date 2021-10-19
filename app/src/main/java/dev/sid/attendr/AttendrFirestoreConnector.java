package dev.sid.attendr;

import android.util.Log;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AttendrFirestoreConnector {

    private static final String TAG = "AttendrFsCon";
    private final FirebaseFirestore db;
    int count = 0;

    public AttendrFirestoreConnector(FirebaseFirestore db) {
        this.db = db;
    }

    public void getDocument(AttendrCallback attendrCallback) {
        // [START get_document]
        DocumentReference docRef = db.collection("attendr").document("test");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    count = Integer.parseInt(Objects.requireNonNull(document.getData()).values().toArray()[0].toString());
                    attendrCallback.getCount(count);
                    Log.d(TAG, "DocumentSnapshot data: " + count);
                } else {
                    attendrCallback.getCount(count);
                    Log.d(TAG, "No such document");
                }
            } else {
                attendrCallback.getCount(count);
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
        // [END get_document]
    }

    public void updateDocument(int count) {
        // [START get_document]
        count+=1;
        DocumentReference docRef = db.collection("attendr").document("test");
        docRef.update("count", count).addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
        .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
        // [END get_document]
    }

}
