package com.example.bevasarlas_oraimunka;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, egysegarEditText, mennyisegEditText, mertekegysegEditText, bruttoArEditText;
    private Button addButton, listButton;

    private RetrofitApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);

        addButton.setOnClickListener(view -> {
            if (validateInputs()) {
                String name = nameEditText.getText().toString();
                int egysegar = Integer.parseInt(egysegarEditText.getText().toString());
                double mennyiseg = Double.parseDouble(mennyisegEditText.getText().toString());
                String mertekegyseg = mertekegysegEditText.getText().toString();
                double brutto_ar = Double.parseDouble(bruttoArEditText.getText().toString());

                Termekek newTermek = new Termekek(name, egysegar, mennyiseg, mertekegyseg, brutto_ar);
                createTermek(newTermek);

                nameEditText.setText("");
                egysegarEditText.setText("");
                mennyisegEditText.setText("");
                mertekegysegEditText.setText("");
                bruttoArEditText.setText("");
            }
        });

        listButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
        });
    }

    private void init(){
        nameEditText = findViewById(R.id.nameEditText);
        egysegarEditText = findViewById(R.id.egysegarEditText);
        mennyisegEditText = findViewById(R.id.mennyisegEditText);
        mertekegysegEditText = findViewById(R.id.mertekegysegEditText);
        bruttoArEditText = findViewById(R.id.bruttoArEditText);
        addButton = findViewById(R.id.addButton);
        listButton = findViewById(R.id.listButton);
    }

    private boolean validateInputs() {
        if (nameEditText.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "A név mező nem lehet üres!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (egysegarEditText.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Az egységár mező nem lehet üres!", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(egysegarEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Az egységár mezőnek számnak kell lennie!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mennyisegEditText.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "A mennyiség mező nem lehet üres!", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(mennyisegEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "A mennyiség mezőnek számnak kell lennie!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mertekegysegEditText.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "A mértékegység mező nem lehet üres!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bruttoArEditText.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "A bruttó ár mező nem lehet üres!", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(bruttoArEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "A bruttó ár mezőnek számnak kell lennie!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createTermek(Termekek termek) {
        Call<Termekek> call = apiService.createTermek(termek);
        call.enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Termék hozzáadva!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Hiba történt!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hálózati hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}