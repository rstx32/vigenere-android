package com.example.vigenere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DekripsiActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 10;
    private static final int READ_PHONE_SATE_PERMISSION_CODE = 11;
    EditText pesan, kunci,  hasil;
    Button dekripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dekripsi);
        pesan = findViewById(R.id.dekripsi_editText_tulisPesan);
        kunci = findViewById(R.id.dekripsi_editText_masukanKunci);
        hasil = findViewById(R.id.dekripsi_editText_hasilDekripsi);
        dekripsi = findViewById(R.id.dekripsi_buttonDekripsi);
    }

    public void dekripsi_dekripsi(View view) {
        try {
            String cipher = pesan.getText().toString();
            String key = kunci.getText().toString();
            Kreatif k = new Kreatif(null,cipher,key);
            Vigenere v = new Vigenere(null,k.getCipher(),key);
            hasil.setText(v.getPlain());
        }catch (Exception ex){
            Toast.makeText(DekripsiActivity.this, String.valueOf(ex), Toast.LENGTH_SHORT).show();
        }
    }
}