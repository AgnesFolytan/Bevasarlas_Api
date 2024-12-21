package com.example.bevasarlas_oraimunka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    private List<Termekek> termekekList;
    private RetrofitApiService apiService;
    private ListView listView;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        Button backButton = findViewById(R.id.backButton);
        listView = findViewById(R.id.listView);

        apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);

        termekekList = new ArrayList<>();
        listAdapter = new ListAdapter(termekekList, this);
        listView.setAdapter(listAdapter);

        fetchTermekek();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Termekek selectedTermek = termekekList.get(position);

            int termekId = -1;
            for (int i = 0; i < termekekList.size(); i++) {
                Termekek currentTermek = termekekList.get(i);
                if (selectedTermek.equals(currentTermek)) {
                    termekId = i;
                    break;
                }
            }

            if (termekId == -1) {
                Toast.makeText(ListActivity.this, "Item not found!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(ListActivity.this, TermekActivity.class);
            intent.putExtra("termek_id", termekId);
            startActivity(intent);
        }
    });
    }

    private void fetchTermekek() {
        Toast.makeText(ListActivity.this, "Kérem várjon egy kicsit", Toast.LENGTH_SHORT).show();
        Call<List<Termekek>> call = apiService.getAllTermek();
        call.enqueue(new Callback<List<Termekek>>() {
            @Override
            public void onResponse(Call<List<Termekek>> call, Response<List<Termekek>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    termekekList.clear();
                    termekekList.addAll(response.body());
                    listAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Termekek>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
