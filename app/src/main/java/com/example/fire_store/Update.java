package com.example.fire_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    EditText t1,t2;
    Button bn1;
    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        bn1 = findViewById(R.id.bn1);
        fb = FirebaseFirestore.getInstance();

        bn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = t1.getText().toString().trim();
                String rename = t2.getText().toString().trim();
                t1.setText("");
                t2.setText("");
                UpdateData(name,rename);
            }
        });
    }

    private void UpdateData(String name, String rename) {
        Map<String ,Object> userdetails = new HashMap<>();
        userdetails.put("name",rename);

        fb.collection("students").whereEqualTo("name",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String DocumentId = documentSnapshot.getId();


                    fb.collection("students").document(DocumentId).update(userdetails).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Update.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Update.this, "update failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                    startActivity(new Intent(Update.this,Read_data.class));
                }
            }
        });


    }
}