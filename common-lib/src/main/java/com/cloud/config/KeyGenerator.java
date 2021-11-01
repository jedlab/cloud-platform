package com.cloud.config;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyGenerator
{

    public static final int KEYLENGTH = 1024;
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String pubPath = USER_HOME + File.separator + "publicKey";
    public static final String privatePath = USER_HOME + File.separator + "privateKey";

    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeyGenerator()
    {
        try
        {
            this.keyGen = KeyPairGenerator.getInstance("RSA");
        }
        catch (NoSuchAlgorithmException e)
        {
            log.info("NoSuchAlgorithmException : " + e.getMessage());
        }
        this.keyGen.initialize(KEYLENGTH);

    }

    public PrivateKey getPrivateKey()
    {
        return this.privateKey;
    }

    public PublicKey getPublicKey()
    {
        return this.publicKey;
    }

    public void init()
    {
        this.pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public boolean keyExists()
    {
        File privateFile = new File(privatePath);
        File pubFile = new File(pubPath);
        return pubFile.exists() && privateFile.exists();
    }

    public File getPubFile()
    {
        return new File(pubPath);
    }

    public File getPrivateFile()
    {
        return new File(privatePath);
    }

    public void writeToFile()
    {

        try
        {
            File privateFile = new File(privatePath);
            File pubFile = new File(pubPath);
            if (privateFile.exists())
                if (privateFile.delete())
                    log.info("private key is deleted");
            if (pubFile.exists())
                if (pubFile.delete())
                    log.info("pub key is deleted");
            Files.write(Paths.get(pubPath), getPublicKey().getEncoded(), StandardOpenOption.CREATE);
            Files.write(Paths.get(privatePath), getPrivateKey().getEncoded(), StandardOpenOption.CREATE);
        }
        catch (IOException e)
        {
            log.info("IOException : {}", e);
        }
    }

    public KeyBase64 asBase64()
    {

        try
        {
            if (keyExists() == false)
            {
                init();
                writeToFile();
            }
            StringBuilder ps = new StringBuilder();
            ps.append("-----BEGIN PUBLIC KEY-----").append(Base64.encodeBase64String(Files.readAllBytes(getPubFile().toPath())))
                    .append("-----END PUBLIC KEY-----");
            //
            StringBuilder ps2 = new StringBuilder();
            ps2.append("-----BEGIN RSA PRIVATE KEY-----").append(Base64.encodeBase64String(Files.readAllBytes(getPrivateFile().toPath())))
                    .append("-----END RSA PRIVATE KEY-----");
            return new KeyBase64(ps.toString(), ps2.toString());
        }
        catch (IOException e)
        {
            log.info("IOException : {}", e);
        }
        return new KeyBase64();
    }

    public KeyPair loadKeyPair()
    {
        try
        {
            if (keyExists() == false)
            {
                init();
                writeToFile();
            }
            // private key
            byte[] keyBytes = Files.readAllBytes(getPrivateFile().toPath());

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privateK = kf.generatePrivate(spec);
            // pub key
            byte[] pubkeyBytes = Files.readAllBytes(getPubFile().toPath());

            X509EncodedKeySpec pubspec = new X509EncodedKeySpec(pubkeyBytes);
            KeyFactory pubkf = KeyFactory.getInstance("RSA");
            PublicKey generatePublic = pubkf.generatePublic(pubspec);
            return new KeyPair(generatePublic, privateK);
        }
        catch (IOException e)
        {
            log.info("IOException : {}", e);
        }
        catch (NoSuchAlgorithmException e)
        {
            log.info("NoSuchAlgorithmException : {}", e);
        }
        catch (InvalidKeySpecException e)
        {
            log.info("InvalidKeySpecException : {}", e);
        }
        return null;
    }

    public static class KeyBase64
    {
        private String pub;
        private String privateKey;

        public String getPub()
        {
            return pub;
        }

        public void setPub(String pub)
        {
            this.pub = pub;
        }

        public String getPrivateKey()
        {
            return privateKey;
        }

        public void setPrivateKey(String privateKey)
        {
            this.privateKey = privateKey;
        }

        public KeyBase64(String pub, String privateKey)
        {
            this.pub = pub;
            this.privateKey = privateKey;
        }

        public KeyBase64()
        {
        }

    }

}