package com.cloud.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptionDecryptionURLParam
{
    public static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    
    public static final String AES_KEY = "MUPRA1234567890P";

    /**
     * @param encodedTxt
     * @param key
     *            : 16 characters
     * @return
     */
    public static String decrypt(String encodedTxt, String keyVal)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            SecretKeySpec key = new SecretKeySpec(keyVal.getBytes(), "AES");

            // encryption url
            cipher.init(Cipher.DECRYPT_MODE, key);
            String decodeStr = URLDecoder.decode(encodedTxt, StandardCharsets.UTF_8.toString());
            System.out.println("URL Decoder String :" + decodeStr);
            // Decode URl safe to base 64
            byte[] base64decodedTokenArr = Base64.decodeBase64(decodeStr.getBytes());

            byte[] decryptedPassword = cipher.doFinal(base64decodedTokenArr);
            // byte[] decryptedPassword = cipher.doFinal(decodeStr.getBytes());
            String decodeTxt = new String(decryptedPassword);
            return decodeTxt;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String encrypt(String val, String keyVal)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
            String timestamp = sdf.format(new Date());
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            SecretKeySpec key = new SecretKeySpec(keyVal.getBytes(), "AES");

            // encryption url
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = cipher.doFinal(val.getBytes());
            System.out.println("encrypted token size:" + cipherText.length);
            // Encode Character which are not allowed on URL
            String encodedTxt = Base64.encodeBase64URLSafeString(cipherText);
            return encodedTxt;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) throws Exception
//    {
//        String key = "1234567890987654";
//        String encrypt = encrypt("omid", key);
//        System.out.println(encrypt);
//        System.out.println(decrypt(encrypt, key));
//
//    }

}