package com.example.vigenere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private static final int SMS_PERMISSION_CODE = 10;
    private static final int READ_PHONE_SATE_PERMISSION_CODE = 11;
    EditText pesan, kunci, nohp, hasil;
    Button enkripsi, kirimSms, kirimWa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pesan = findViewById(R.id.enkripsi_editText_tulisPesan);
        kunci = findViewById(R.id.enkripsi_editText_masukanKunci);
        nohp = findViewById(R.id.enkripsi_editText_noHP);
        hasil = findViewById(R.id.enkripsi_editText_hasilEnkripsi);
        enkripsi = findViewById(R.id.enkripsi_buttonEnkripsi);
        kirimSms = findViewById(R.id.enkripsi_buttonKirimSms);
        kirimWa = findViewById(R.id.enkripsi_buttonKirimWa);
    }

    public void enkripsi_kirimSms(View view) {
        kirimSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(
                        Manifest.permission.SEND_SMS, SMS_PERMISSION_CODE
                );
                checkPermission(
                        Manifest.permission.READ_PHONE_STATE,READ_PHONE_SATE_PERMISSION_CODE
                );
                KirimSMS();
            }
        });
    }

    public void KirimSMS() {
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(
                    "+" + nohp.getText().toString(),
                    null,
                    hasil.getText().toString(),
                    null,
                    null
            );
            Toast.makeText(getApplicationContext(), "SMS berhasil dikirim", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), String.valueOf(ex), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(MainActivity.this, "Permission sudah diijinkan", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "SMS permission diijinkan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "SMS permission ditolak", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == READ_PHONE_SATE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "READ PHONE STATE permission diijinkan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "READ PHONE STATE permission ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean cekLetter(String teks){
        int[] cek = new int[teks.length()];
        for(int i=0; i<teks.length(); i++){
            cek[i] = teks.charAt(i);
            if((cek[i]>122 || cek[i]<97) && cek[i]!=32){
                return true;
            }
        }
        return false;
    }

    public void enkripsi_enkripsi(View view) {
        try {
            String plain = pesan.getText().toString();
            String key = kunci.getText().toString();
            if(key.contains(" ")){
                Toast.makeText(MainActivity.this, "Kunci Tidak Boleh Ada Spasi!", Toast.LENGTH_SHORT).show();
                kirimSms.setEnabled(false);
                kirimWa.setEnabled(false);
            }else if(cekLetter(plain) || cekLetter(key)){
                Toast.makeText(MainActivity.this, "Hanya Boleh Alfabet Lowercase!", Toast.LENGTH_SHORT).show();
                kirimSms.setEnabled(false);
                kirimWa.setEnabled(false);
            }else{
                kirimSms.setEnabled(true);
                kirimWa.setEnabled(true);
            }
            Vigenere v = new Vigenere(plain,null,key);
            hasil.setText(v.getCipher());
        }catch (Exception ex){
            Toast.makeText(MainActivity.this, String.valueOf(ex), Toast.LENGTH_SHORT).show();
        }
    }

    public void openWA() {
        String message = hasil.getText().toString();
        String phone = nohp.getText().toString();

        Intent WA = new Intent();
        WA.setAction(Intent.ACTION_SEND);
        WA.putExtra(Intent.EXTRA_TEXT, message);
        WA.putExtra("jid", phone + "@s.whatsapp.net");
        WA.setType("text/plain");
        WA.setPackage("com.whatsapp");
        startActivity(WA);
    }

    public void enkripsi_kirimWa(View view) {
        openWA();
    }
}
