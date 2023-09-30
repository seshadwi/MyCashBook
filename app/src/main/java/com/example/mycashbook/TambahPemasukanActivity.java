package com.example.mycashbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mycashbook.databinding.ActivityTambahPemasukanBinding;
import com.example.mycashbook.utils.DatabaseHelper;
import com.example.mycashbook.utils.Utils;

public class TambahPemasukanActivity extends AppCompatActivity {

    private ActivityTambahPemasukanBinding binding;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tambah_pemasukan);
        utils = new Utils();

        binding.etTanggalPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.showDateDialog(TambahPemasukanActivity.this
                        , binding.etTanggalPemasukan);
            }
        });

        utils.setCurrency(binding.etNominalPemasukan);

        binding.btSimpanPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEditTextValidation()) {
                    addPemasukanProcess();
                    Toast.makeText(getApplicationContext()
                            , "Data berhasil disimpan"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btKembaliPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.moveToMenu(TambahPemasukanActivity.this, MainActivity.class);
            }
        });

    }

    private boolean checkEditTextValidation() {
        boolean status = true;
        if (binding.etTanggalPemasukan.getText().length() == 0) {
            Toast.makeText(this, "Tanggal masih kosong!"
                    , Toast.LENGTH_SHORT).show();
            status = false;
        } else if (binding.etNominalPemasukan.getText().length() == 0
                || binding.etNominalPemasukan.getText().toString().equals("0")) {
            Toast.makeText(this, "Nomimal masih kosong!",
                    Toast.LENGTH_SHORT).show();
            status = false;
        } else if (binding.etKeteranganPemasukan.getText().length() == 0) {
            Toast.makeText(this, "Keterangan masih kosong!",
                    Toast.LENGTH_SHORT).show();
            status = false;
        }

        return status;
    }

    private void addPemasukanProcess(){
        String tanggal = binding.etTanggalPemasukan.getText().toString().trim();
        String nominal = binding.etNominalPemasukan.getText().toString().trim();
        String keterangan = binding.etKeteranganPemasukan.getText().toString().trim();
        String kategori = "pemasukan";

        nominal = nominal.replace(".","");

        DatabaseHelper dbHelper = new DatabaseHelper(TambahPemasukanActivity.this);
        dbHelper.addKeuangan(tanggal, nominal, keterangan, kategori);

        binding.etTanggalPemasukan.setText("");
        binding.etNominalPemasukan.setText("");
        binding.etKeteranganPemasukan.setText("");

    }
}