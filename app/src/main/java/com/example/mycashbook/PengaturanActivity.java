package com.example.mycashbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mycashbook.databinding.ActivityPengaturanBinding;
import com.example.mycashbook.utils.Preferences;
import com.example.mycashbook.utils.Utils;

public class PengaturanActivity extends AppCompatActivity {

    private ActivityPengaturanBinding binding;
    private Utils util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pengaturan);
        util = new Utils();

        binding.btSimpanPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEditTextValidation()) {
                    changePassword();
                }
            }
        });

        binding.btKembaliPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.moveToMenu(PengaturanActivity.this, MainActivity.class);
            }
        });
    }

    private Boolean checkEditTextValidation() {
        boolean status = true;
        if (binding.etPasswordPengaturanLama.getText().length() == 0) {
            Toast.makeText(this, "Password lama masih kosong!"
                    , Toast.LENGTH_SHORT).show();
            status = false;
        } else if (binding.etPasswordPengaturanBaru.getText().length() == 0) {
            Toast.makeText(this, "Password baru masih kosong!",
                    Toast.LENGTH_SHORT).show();
            status = false;
        }

        return status;
    }

    private void changePassword(){
        String password_lama = Preferences.getKeyPassword(getApplicationContext());
        String et_password_lama_value = binding.etPasswordPengaturanLama.getText().toString();
        String et_password_baru_value = binding.etPasswordPengaturanBaru.getText().toString();

        if (password_lama.equals(et_password_lama_value)){
            Preferences.setKeyPassword(getApplicationContext(), et_password_baru_value);
            Toast.makeText(getApplicationContext(), "Password berhasil diubah"
                    , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Password saat ini salah!"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}