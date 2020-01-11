package ch.m1m.jwt;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.openssl.PKCS8Generator;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

// https://www.bouncycastle.org/wiki/display/JA1/Elliptic+Curve+Key+Pair+Generation+and+Key+Factories
//
// pkcs12 .p12  http://www.ssekhon.com/blog/2017/08/02/sign-data-using-ecdsa-and-bouncy-castle

public class CryptoEC {

    public static void main(String...args) {

        // ConfigurableProvider.EC_IMPLICITLY_CA

        Security.addProvider(new BouncyCastleProvider());

        doEcKeys();

        //doRSAKeys();

    }


    public static void doEcKeys() {

        // load keys
        // https://gist.github.com/wuyongzheng/0e2ed6d8a075153efcd3#file-ecdh_bc-java-L47-L50
        //

        try {
            //String ecCurveName = "prime192v1";

            // NIST cures ... FIPS
            String ecCurveName = "P-256";

            ECGenParameterSpec ecGenSpec = new ECGenParameterSpec(ecCurveName);
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");
            g.initialize(ecGenSpec, new SecureRandom());
            KeyPair kp = g.generateKeyPair();

            ECPrivateKey privKey = (ECPrivateKey)kp.getPrivate();
            ECPublicKey publKey = (ECPublicKey)kp.getPublic();

            String pemKey = object2PEM(privKey);
            System.out.println(pemKey);

            // PKCS8 private key
            //PKCS8Generator pkcs8 = new PKCS8Generator(privKey, PKCS8Generator.AES_256_CBC, "BC");

            // read private key from string
            //
            //

            System.out.println("... from string ...");
            Reader rdr = new StringReader(pemKey);
            org.bouncycastle.util.io.pem.PemObject spki = new org.bouncycastle.util.io.pem.PemReader(rdr).readPemObject();
            PrivateKey privKeyFromString = KeyFactory.getInstance("ECDSA","BC").generatePrivate(new PKCS8EncodedKeySpec(spki.getContent()));

            pemKey = object2PEM(privKeyFromString);
            System.out.println(pemKey);

            // print public key
            //
            pemKey = object2PEM(publKey);
            System.out.println(pemKey);

            /*
            // read public key from string (X509 format)
            // https://stackoverflow.com/questions/40434317/how-to-load-pem-encoded-elliptic-curve-public-keys-into-bouncy-castle
            //
            System.out.println("... from string ...");
            Reader rdr = new StringReader(pemKey);
            org.bouncycastle.util.io.pem.PemObject spki = new org.bouncycastle.util.io.pem.PemReader(rdr).readPemObject();
            PublicKey pubKeyFromString = KeyFactory.getInstance("ECDSA","BC").generatePublic(new X509EncodedKeySpec(spki.getContent()));

            pemKey = object2PEM(pubKeyFromString);
            System.out.println(pemKey);
            */


        } catch (NoSuchProviderException e) {
            System.err.println(e);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    public static void doRSAKeys() {

        try {
            KeyPair kp = genKeys("RSA");

            PrivateKey privKey = kp.getPrivate();
            PublicKey publKey = kp.getPublic();

            String pemKey = object2PEM(privKey);
            System.out.println(pemKey);
            pemKey = object2PEM(publKey);
            System.out.println(pemKey);

        } catch (NoSuchProviderException e) {
            System.err.println(e);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // call with keyType "RSA" or "EC"
    //
    public static KeyPair genKeys(String keyType) throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyType, BouncyCastleProvider.PROVIDER_NAME);
        KeyPair kp = keyGen.generateKeyPair();
        return kp;
    }


    public static String object2PEM(Object object) throws IOException {
        StringWriter sw = new StringWriter();
        JcaPEMWriter writer = new JcaPEMWriter(sw);
        writer.writeObject(object);
        writer.close();
        return sw.getBuffer().toString();
    }

}
