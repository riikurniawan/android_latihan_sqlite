package com.rii.latihansqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnStore, btnGetAll, btnTruncate;
    private EditText etNames;
    private TextView tvNames;
    private ArrayList<String> arrayList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        etNames = findViewById(R.id.etName);
        btnStore = findViewById(R.id.btnStore);
        btnGetAll = findViewById(R.id.btnGetAll);
        btnTruncate = findViewById(R.id.btnTruncate);
//        tvNames = findViewById(R.id.tvNames);

        ListView lvName = findViewById(R.id.lvNames);
        ArrayList<String> studentList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        lvName.setAdapter(adapter);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etNames.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Fill Name!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        databaseHelper.addStudentDetail(etNames.getText().toString());
                        etNames.setText("");

                        Toast.makeText(MainActivity.this, "Stored successfully!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList = databaseHelper.getAllStudentsList();

                etNames.setText("");
                studentList.clear();
                adapter.notifyDataSetChanged();
                for (String item:arrayList) {
//                    tvNames.append(item + " ");
                studentList.add(item);
                adapter.notifyDataSetChanged();
                }
            }
        });

        btnTruncate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    databaseHelper.truncateStudentlist();
                    studentList.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "You just truncated the table!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}