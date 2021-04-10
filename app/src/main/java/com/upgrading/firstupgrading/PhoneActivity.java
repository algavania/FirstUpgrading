package com.upgrading.firstupgrading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PhoneActivity extends AppCompatActivity {

    private ArrayList<PhoneModel> arrayList;
    private RecyclerView recyclerView;
    private PhoneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        recyclerView = findViewById(R.id.rv_main);
        TextView tv_item = findViewById(R.id.tv_item);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            arrayList = new ArrayList<PhoneModel>();
            arrayList = (ArrayList<PhoneModel>) getIntent().getSerializableExtra("arrayList");

            if (arrayList == null) {
                if (arrayList.isEmpty()) tv_item.setVisibility(View.VISIBLE);
                else tv_item.setVisibility(View.INVISIBLE);
            }
            else tv_item.setVisibility(View.INVISIBLE);

            adapter = new PhoneAdapter(getApplicationContext(), arrayList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new PhoneAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getApplicationContext(), arrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}