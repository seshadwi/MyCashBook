package com.example.mycashbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.mycashbook.databinding.ActivityDetailCashFlowBinding;
import com.example.mycashbook.models.KeuanganModel;
import com.example.mycashbook.utils.DatabaseHelper;
import com.example.mycashbook.utils.MyAdapter;
import com.example.mycashbook.utils.Utils;

import java.util.ArrayList;

public class DetailCashFlowActivity extends AppCompatActivity {

    private ActivityDetailCashFlowBinding binding;
    private Utils util;
    private DatabaseHelper dbHelper;
    private ArrayList<KeuanganModel> keuanganArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cash_flow);
        util = new Utils();

        dbHelper = new DatabaseHelper(DetailCashFlowActivity.this);

        keuanganArrayList = new ArrayList<>();

        storeDataInArrays();

        MyAdapter adapter = new MyAdapter(DetailCashFlowActivity.this, keuanganArrayList);

        binding.rvKeuangan.setAdapter(adapter);
        binding.rvKeuangan.setLayoutManager(new LinearLayoutManager(
                DetailCashFlowActivity.this));

        binding.btKembaliCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.moveToMenu(DetailCashFlowActivity.this, MainActivity.class);
            }
        });
    }

    private void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllData();
        if(cursor.getCount() == 0){
            binding.imEmpty.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                int id_keuangan = cursor.getInt(0);
                String tanggal = cursor.getString(1);
                double nominal = Double.parseDouble(cursor.getString(2));
                String nominal_str = util.setFormatRupiah(nominal);
                String keterangan = cursor.getString(3);
                String kategori = cursor.getString(4);

                keuanganArrayList.add(new KeuanganModel(id_keuangan,
                        tanggal,
                        nominal_str,
                        keterangan,
                        kategori));

            }
            binding.imEmpty.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.GONE);
        }
    }
}