package com.example.vigenere;

class Vigenere{
    private String cipher;
    private String plain;
    private String key;

    public Vigenere(String plain, String cipher, String key){
        super();
        if(cipher==null){
            this.plain = plain;
            this.cipher = enkripsi(plain, key);
        }else if(plain==null){
            this.cipher = cipher;
            this.plain = dekripsi(cipher, key);
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

    public static String enkripsi(String plain, String key){
        if(cekAlfabet(plain)==true || cekAlfabet(key)==true){
            return (new String("Hanya Boleh Alfabet Lowercase!"));
        }else{
            char[] tampung = new char[plain.length()];
            String hasilKey;
            hasilKey = new String(adaptasiKey(key, plain));
            if(hasilKey.contains("xxx")){
                return (new String("Key Tidak Boleh Ada Spasi!"));
            }else{
                // enkripsi
                for(int i=0; i<plain.length(); i++){
                    if(hasilKey.charAt(i)==' '){
                        tampung[i]=' ';
                        continue;
                    }
                    int x = (plain.charAt(i)-97) + (hasilKey.charAt(i)-97);
                    if(x>=26)
                        x=(x-26)+97;
                    else
                        x+=97;

                    tampung[i]= (char)x;
                }
                return (new String(tampung));
            }
        }
    }

    public static String dekripsi(String cipher, String key){
        if(cekAlfabet(cipher)==true || cekAlfabet(key)==true){
            return (new String("Hanya Boleh Alfabet Lowercase!"));
        }else{
            char[] tampung = new char[cipher.length()];
            String hasilKey;
            hasilKey = new String(adaptasiKey(key, cipher));

            // dekripsi
            for(int i=0; i<cipher.length(); i++){
                if(hasilKey.charAt(i)==' '){
                    tampung[i]=' ';
                    continue;
                }
                int x = (cipher.charAt(i)-97) - (hasilKey.charAt(i)-97);
                if(x<0)
                    x+=123;
                else
                    x+=97;

                tampung[i]= (char)x;
            }
            return (new String(tampung));
        }
    }

    public static String adaptasiKey(String key, String adaptasi){
        char[] tampungKey   = new char[adaptasi.length()];

        // menyesuaikan key dengan panjang adaptasi
        for(int i=0, j=i; i<adaptasi.length(); i++){
            // tambahkan spasi
            if(adaptasi.charAt(i)==' '){
                tampungKey[i]=' ';
                continue;
            }else{
                if(key.charAt(j)==' '){
                    return new String("xxx");
                }
                tampungKey[i] = key.charAt(j);
            }

            // ulang ke awal jika key sudah berakhir
            if(j==key.length()-1)
                j=0;
            else
                j++;
        }
        return (new String(tampungKey));
    }

    public static boolean cekAlfabet(String teks){
        int[] cek = new int[teks.length()];
        for(int i=0; i<teks.length(); i++){
            cek[i] = teks.charAt(i);
            if((cek[i]>122 || cek[i]<97) && cek[i]!=32){
                return true;
            }
        }
        return false;
    }
}