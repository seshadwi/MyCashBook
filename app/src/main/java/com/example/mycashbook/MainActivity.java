package com.example.mycashbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.mycashbook.databinding.ActivityMainBinding;
import com.example.mycashbook.utils.DatabaseHelper;
import com.example.mycashbook.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Utils util;
    private DatabaseHelper dbHelper;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        util = new Utils();
        dbHelper = new DatabaseHelper(getApplicationContext());

        String rangkumanPemasukan = dbHelper.getRangkumanKeuangan("pemasukan");
        String rangkumanPengeluaran = dbHelper.getRangkumanKeuangan("pengeluaran");

        rangkumanPemasukan = util.setFormatRupiah(Double.parseDouble(rangkumanPemasukan));
        rangkumanPengeluaran = util.setFormatRupiah(Double.parseDouble(rangkumanPengeluaran));

        binding.tvPemasukanInfoValue.setText(rangkumanPemasukan);
        binding.tvPengeluaranInfoValue.setText(rangkumanPengeluaran);

        binding.clTambahPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.moveToMenu(MainActivity.this, TambahPemasukanActivity.class);
            }
        });

        binding.clTambahPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.moveToMenu(MainActivity.this, TambahPengeluaranActivity.class);
            }
        });

        binding.clDetailCashFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.moveToMenu(MainActivity.this, DetailCashFlowActivity.class);
            }
        });

        binding.clPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.moveToMenu(MainActivity.this, PengaturanActivity.class);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Lakukan lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}