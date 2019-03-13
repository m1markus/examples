package ch.m1m.ldap;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.spec.ECGenParameterSpec;

public class EccExampleCrypto {

    private static String keyFileName = "/tmp/keystore.p12";
    private static char[] keyFilePassword = "changeit".toCharArray();

    public static void main(String[] args) throws Exception {

        // provider "BC"
        Security.addProvider(new BouncyCastleProvider());

        bcExample();

        /*
        storeSingleKey();
        loadKey();
        generateNewECKeys();
        */
    }

    // from: https://github.com/joschi/cryptoworkshop-bouncycastle/blob/master/src/main/java/cwguide/JcePKCS12Example.java
    //
    public static void bcExample() {

    }


    public static void generateNewECKeys() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", "SunEC");
        ECGenParameterSpec ecsp = new ECGenParameterSpec("secp192r1");
        kpg.initialize(ecsp);

        KeyPair kp = kpg.genKeyPair();
        PrivateKey privKey = kp.getPrivate();
        PublicKey pubKey = kp.getPublic();

        System.out.println("privKey: " + privKey.toString());
        System.out.println("pubKey:  " + pubKey.toString());
    }


    public static void loadKey() {
    }


    // from: https://neilmadden.blog/2017/11/17/java-keystores-the-gory-details/
    //
    public static void storeSingleKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null); // Initialize a blank keystore
        SecretKey key = new SecretKeySpec(new byte[32], "AES");
        byte[] salt = new byte[20];
        new SecureRandom().nextBytes(salt);
        keyStore.setEntry("test", new SecretKeyEntry(key),
                new PasswordProtection(keyFilePassword,
                        "PBEWithHmacSHA512AndAES_128",
                        new PBEParameterSpec(salt, 100_000)));
        keyStore.store(new FileOutputStream(keyFileName), keyFilePassword);
    }
}
