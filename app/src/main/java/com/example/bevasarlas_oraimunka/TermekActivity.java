package com.example.bevasarlas_oraimunka;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermekActivity extends AppCompatActivity {
    private EditText nameEditText, egysegarEditText, mennyisegEditText, mertekegysegEditText, bruttoArEditText;
    private Button modositasButton, torlesButton, megseButton;
    private RetrofitApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termek_edit);

        init();

        apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);

        Intent intent = getIntent();

        int termekId = intent.getIntExtra("termek_id", -1); // -1 is the default value if termek_id is not found

        modositasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                int egysegar;
                try { egysegar = Integer.parseInt(egysegarEditText.getText().toString());
                } catch (NumberFormatException e) { egysegar = 0; }

                double mennyiseg;
                try { mennyiseg = Double.parseDouble(mennyisegEditText.getText().toString());
                } catch (NumberFormatException e) { mennyiseg = 0.0; }

                String mertekegyseg = mertekegysegEditText.getText().toString();

                double brutto_ar;
                try { brutto_ar = Double.parseDouble(bruttoArEditText.getText().toString());
                } catch (NumberFormatException e) { brutto_ar = 0.0; }

                Termekek newTermek = new Termekek(name, egysegar, mennyiseg, mertekegyseg, brutto_ar);

                termekSet(termekId, newTermek);

                nameEditText.setText("");
                egysegarEditText.setText("");
                mennyisegEditText.setText("");
                mertekegysegEditText.setText("");
                bruttoArEditText.setText("");
            }
        });

        torlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TermekActivity.this);
                builder.setMessage("Szeretné törölni?");
                builder.setTitle("Figyelmeztetés!");
                builder.setCancelable(false);
                builder.setPositiveButton("Igen", (DialogInterface.OnClickListener) (dialog, which) -> {
                    deleteTermek(termekId);
                });
                builder.setNegativeButton("Nem", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        megseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermekActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        nameEditText = findViewById(R.id.nameEditText);
        egysegarEditText = findViewById(R.id.egysegarEditText);
        mennyisegEditText = findViewById(R.id.mennyisegEditText);
        mertekegysegEditText = findViewById(R.id.mertekegysegEditText);
        bruttoArEditText = findViewById(R.id.bruttoArEditText);
        modositasButton = findViewById(R.id.modositasButton);
        torlesButton = findViewById(R.id.torlesButton);
        megseButton = findViewById(R.id.megseButton);
    }

    private void termekSet(int termekId, Termekek termek) {
        Call<Termekek> call = apiService.getTermekById(termekId);
        call.enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Termekek regiTermek = response.body();

                    String name = termek.getNev().isEmpty() ? regiTermek.getNev() : termek.getNev();
                    int egysegar = termek.getEgysegar() == 0 ? regiTermek.getEgysegar() : termek.getEgysegar();
                    double mennyiseg = termek.getMennyiseg() == 0.0 ? regiTermek.getMennyiseg() : termek.getMennyiseg();
                    String mertekegyseg = termek.getMertekegyseg().isEmpty() ? regiTermek.getMertekegyseg() : termek.getMertekegyseg();
                    double brutto_ar = termek.getBrutto_ar() == 0.0 ? regiTermek.getBrutto_ar() : termek.getBrutto_ar();

                    Termekek updatedTermek = new Termekek(name, egysegar, mennyiseg, mertekegyseg, brutto_ar);

                    editTermek(termekId, updatedTermek);
                } else {
                    Toast.makeText(TermekActivity.this, "Failed to fetch the product!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Toast.makeText(TermekActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void editTermek(int termekId, Termekek termek) {
        Call<Termekek> call = apiService.updateTermek(termekId, termek);
        call.enqueue(new Callback<Termekek>() {
            @Override
            public void onResponse(Call<Termekek> call, Response<Termekek> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TermekActivity.this, "Termék módosítva!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TermekActivity.this, "Hiba történt!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Termekek> call, Throwable t) {
                Toast.makeText(TermekActivity.this, "Hálózati hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTermek(int termekId) {
        Call<Void> call = apiService.deleteTermek(termekId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TermekActivity.this, "Termék törölve!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TermekActivity.this, "Hiba történt!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TermekActivity.this, "Hálózati hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
