package com.example.vigenere;

import java.util.*;

public class Kreatif{
    private String cipher;
    private String plain;
    private String key;

    public Kreatif(String plain, String cipher, String key){
        super();
        if(cipher==null){
            this.plain = plain;
            this.cipher = enkripsi(key, plain);
        }else if(plain==null){
            this.cipher = cipher;
            this.plain = dekripsi(key, cipher);
        }
        this.key = key;
    }

    public String getPlain(){
        return this.plain;
    }

    public String getCipher(){
        return this.cipher;
    }

    public String getKey(){
        return this.key;
    }

    public static String strToBinary(String s){
        int n = s.length();
        String hasil = "";

        for (int i = 0; i < n; i++){
            int val = Integer.valueOf(s.charAt(i));
            String bin = "";

            while (val > 0){
                if (val % 2 == 1)
                    bin += '1';
                else
                    bin += '0';
                val /= 2;
            }

            while(bin.length()<8){
                bin += '0';
            }

            bin = reverse(bin);
            hasil += bin;
        }

        return hasil;
    }

    static String reverse(String input){
        char[] a = input.toCharArray();
        int l, r = 0;
        r = a.length - 1;

        for (l = 0; l < r; l++, r--){
            char temp = a[l];
            a[l] = a[r];
            a[r] = temp;
        }

        return String.valueOf(a);
    }

    public static String addBinary(String plain, String key){
        Long number0 = Long.parseLong(plain, 2);
        Long number1 = Long.parseLong(key, 2);

        Long sum = number0 + number1;
        return Long.toBinaryString(sum);
    }

    public static String enkripsi(String key, String plain){
        int x, hasilPlain=0;

        for(x=0; x<plain.length(); x++){
            hasilPlain += (plain.charAt(x) - 97);
        }
        hasilPlain*=100;
        String hasil = Integer.toString(hasilPlain);

        return addBinary(strToBinary(key),strToBinary(hasil));
    }

    public static String dekripsi(String key, String plain){
        String stringKey = strToBinary(key);
        Long hasilKey = Long.parseLong(stringKey,2);
        Long hasilPlain = Long.parseLong(plain,2);
        Long jumlah = hasilPlain - hasilKey;

        String tes = Long.toBinaryString(jumlah);
        StringBuilder str = new StringBuilder(tes);

        int x=0;
        while(str.length()%8!=0){
            str.insert(x, '0');
            x++;
        }

        String output = "";
        for(int i = 0; i <= str.toString().length() - 8; i+=8){
            int k = Integer.parseInt(str.toString().substring(i, i+8), 2);
            output += (char) k;
        }

        String hasil = Integer.toString(Integer.parseInt(output)/100);

        return hasil;
    }
}