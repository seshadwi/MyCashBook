package com.example.mycashbook.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycashbook.R;
import com.example.mycashbook.models.KeuanganModel;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<KeuanganModel> keuanganArrayList;


    public MyAdapter(Context context, ArrayList<KeuanganModel> keuanganArrayList) {
        this.context = context;
        this.keuanganArrayList = keuanganArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cash_flow, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        KeuanganModel keuanganModel = keuanganArrayList.get(position);
        String tanggal = keuanganModel.getTanggal();
        String nominal = keuanganModel.getNominal();
        String keterangan = keuanganModel.getKeterangan();
        String kategori = keuanganModel.getKategori();

        if (kategori.equals("pengeluaran")){
            holder.tv_kategori.setText("[-]");
            holder.im_kategori.setImageResource(R.drawable.right_arrow);
        }else{
            holder.tv_kategori.setText("[+]");
            holder.im_kategori.setImageResource(R.drawable.left_arrow);
        }

        holder.tv_nominal.setText(nominal);
        holder.tv_keterangan.setText(keterangan);
        holder.tv_tanggal.setText(tanggal);

    }

    @Override
    public int getItemCount() {
        return keuanganArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_kategori, tv_nominal, tv_keterangan, tv_tanggal;
        ImageView im_kategori;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_kategori = itemView.findViewById(R.id.tv_kategori);
            tv_nominal = itemView.findViewById(R.id.tv_nominal);
            tv_keterangan = itemView.findViewById(R.id.tv_keterangan);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            im_kategori = itemView.findViewById(R.id.im_kategori);
        }
    }
}
