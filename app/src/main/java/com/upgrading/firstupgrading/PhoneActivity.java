package com.upgrading.firstupgrading;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PhoneActivity extends AppCompatActivity {

    private ArrayList<PhoneModel> arrayList;
    private RecyclerView recyclerView;
    private PhoneAdapter adapter;

    private final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        recyclerView = findViewById(R.id.rv_main);

        addData();
    }

    private void addData() {
        TextView tv_item = findViewById(R.id.tv_item);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("contacts");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PhoneModel phoneModel = dataSnapshot.getValue(PhoneModel.class);
                    arrayList.add(phoneModel);
                }
                if (arrayList != null) {
                    if (arrayList.isEmpty()) tv_item.setVisibility(View.VISIBLE);
                    else tv_item.setVisibility(View.INVISIBLE);
                } else tv_item.setVisibility(View.INVISIBLE);

                adapter = new PhoneAdapter(getApplicationContext(), arrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new PhoneAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ProgressDialog progressDialog = new ProgressDialog(PhoneActivity.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                        myRef.orderByChild("phone").equalTo(arrayList.get(position).getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String key = "";
                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                    key = childSnapshot.getKey();
                                    Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
                                }

                                progressDialog.dismiss();

                                String name, email, phone, about, gender;
                                int age;

                                name = arrayList.get(position).getName();
                                email = arrayList.get(position).getEmail();
                                phone = arrayList.get(position).getPhone();
                                about = arrayList.get(position).getAbout();
                                gender = arrayList.get(position).getGender();
                                age = arrayList.get(position).getAge();

                                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", phone);
                                intent.putExtra("about", about);
                                intent.putExtra("gender", gender);
                                intent.putExtra("age", age);
                                intent.putExtra("key", key);
                                startActivityForResult(intent, REQUEST_CODE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) addData();
        }
    }
}