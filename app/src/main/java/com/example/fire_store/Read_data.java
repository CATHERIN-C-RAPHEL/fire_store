package com.example.fire_store;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Read_data extends AppCompatActivity {

    FirebaseFirestore Fstore;
    List<String> values = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        Fstore = FirebaseFirestore.getInstance();

        ListView lv = findViewById(R.id.lv);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,values);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Read_data.this,Update.class));
            }
        });

        Fstore.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Read_data.this, "data read", Toast.LENGTH_SHORT).show();
                    for (QueryDocumentSnapshot document : task.getResult()){
//                        HashMap<String ,Object> stud = (HashMap<String, Object>) document.getData();
//                        String name = (String) stud.get("name");
                        Log.d(TAG, document.getId()+"=>"+document.getData());
                        values.add(document.getString("name"));

                    }
                    arrayAdapter.notifyDataSetChanged();


                }
                else{
                    Toast.makeText(Read_data.this, "Data not shown", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
