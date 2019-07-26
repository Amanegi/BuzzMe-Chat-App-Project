package com.example.buzzme.utility;

import android.util.Base64;

public class MsgCrypto {

    public static String encryptMessage(String msg) {
        byte[] encodeValue = Base64.encode(msg.getBytes(), Base64.DEFAULT);
        return new String(encodeValue);
    }

    public static String decryptMessage(String val) {
        byte[] decodeValue = Base64.decode(val.getBytes(), Base64.DEFAULT);
        return new String(decodeValue);
    }
}
