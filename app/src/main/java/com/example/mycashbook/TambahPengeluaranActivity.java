package com.example.mycashbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mycashbook.databinding.ActivityTambahPengeluaranBinding;
import com.example.mycashbook.utils.DatabaseHelper;
import com.example.mycashbook.utils.Utils;

public class TambahPengeluaranActivity extends AppCompatActivity {

    private ActivityTambahPengeluaranBinding binding;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tambah_pengeluaran);
        utils = new Utils();

        binding.etTanggalPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.showDateDialog(TambahPengeluaranActivity.this
                        , binding.etTanggalPengeluaran);
            }
        });

        utils.setCurrency(binding.etNominalPengeluaran);

        binding.btSimpanPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEditTextValidation()) {
                    addPengeluaranProcess();
                }

            }
        });

        binding.btKembaliPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.moveToMenu(TambahPengeluaranActivity.this, MainActivity.class);
            }
        });

    }

    private boolean checkEditTextValidation() {
        boolean status = true;
        if (binding.etTanggalPengeluaran.getText().length() == 0) {
            Toast.makeText(this, "Tanggal masih kosong!"
                    , Toast.LENGTH_SHORT).show();
            status = false;
        } else if (binding.etNominalPengeluaran.getText().length() == 0
                || binding.etNominalPengeluaran.getText().toString().equals("0")) {
            Toast.makeText(this, "Nomimal masih kosong!",
                    Toast.LENGTH_SHORT).show();
            status = false;
        } else if (binding.etKeteranganPengeluaran.getText().length() == 0) {
            Toast.makeText(this, "Keterangan masih kosong!",
                    Toast.LENGTH_SHORT).show();
            status = false;
        }

        return status;
    }

    private void addPengeluaranProcess(){
        String tanggal = binding.etTanggalPengeluaran.getText().toString().trim();
        String nominal = binding.etNominalPengeluaran.getText().toString().trim();
        String keterangan = binding.etKeteranganPengeluaran.getText().toString().trim();
        String kategori = "pengeluaran";

        nominal = nominal.replace(".","");

        DatabaseHelper dbHelper = new DatabaseHelper(TambahPengeluaranActivity.this);
        dbHelper.addKeuangan(tanggal, nominal, keterangan, kategori);

        binding.etTanggalPengeluaran.setText("");
        binding.etNominalPengeluaran.setText("");
        binding.etKeteranganPengeluaran.setText("");

    }
}