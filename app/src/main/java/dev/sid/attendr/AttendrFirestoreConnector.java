package dev.sid.attendr;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendrFirestoreConnector {

    private static final String TAG = "AttendrFsCon";
    private final FirebaseFirestore db;

    public AttendrFirestoreConnector(FirebaseFirestore db) {
        this.db = db;
    }

    public void checkIfOrganizationExists(AttendrCallback attendrCallback, String organization) {
        // [START get_document]
        db.collection("organizations").get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean flag = false;
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    JSONObject jObject  = new JSONObject(document.getData());
                    try {
                        Log.d(TAG, document.getId() + " => " + jObject.getString("name"));
                        if (jObject.getString("name").equalsIgnoreCase(organization)) {
                            flag = true;
                            Log.d(TAG, "DocumentSnapshot data exists");
                            break;
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (flag) {
                    attendrCallback.checkOrganization(true);
                } else {
                    if (!organization.isEmpty()) {
                        addIfOrganizationDoesNotExists(attendrCallback, organization);
                    }
                }
            } else {
                attendrCallback.checkOrganization(false);
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
        // [END get_document]
    }

    private void addIfOrganizationDoesNotExists(AttendrCallback attendrCallback, String organization) {
        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("name", organization);
        db.collection("organizations")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    attendrCallback.checkOrganization(true);
                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    attendrCallback.checkOrganization(false);
                    Log.w(TAG, "Error adding document", e);
                });

    }

//    public void getDocument(AttendrCallback attendrCallback) {
//        // [START get_document]
//        DocumentReference docRef = db.collection("attendr").document("test");
//        docRef.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                DocumentSnapshot document = task.getResult();
//                assert document != null;
//                if (document.exists()) {
//                    count = Integer.parseInt(Objects.requireNonNull(document.getData()).values().toArray()[0].toString());
//                    attendrCallback.getCount(count);
//                    Log.d(TAG, "DocumentSnapshot data: " + count);
//                } else {
//                    attendrCallback.getCount(count);
//                    Log.d(TAG, "No such document");
//                }
//            } else {
//                attendrCallback.getCount(count);
//                Log.d(TAG, "get failed with ", task.getException());
//            }
//        });
//        // [END get_document]
//    }

//    public void updateDocument(int count) {
//        // [START get_document]
//        DocumentReference docRef = db.collection("attendr").document("test");
//        docRef.update("count", count).addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
//        .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
//        // [END get_document]
//    }

}
