package ch.m1m.ldap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.PKCS8Generator;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.bc.BcDefaultDigestProvider;
import org.bouncycastle.pkcs.PKCS12PfxPdu;
import org.bouncycastle.pkcs.PKCS12PfxPduBuilder;
import org.bouncycastle.pkcs.PKCS12SafeBag;
import org.bouncycastle.pkcs.PKCS12SafeBagBuilder;
import org.bouncycastle.pkcs.PKCS12SafeBagFactory;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.bc.BcPKCS12MacCalculatorBuilderProvider;
import org.bouncycastle.pkcs.jcajce.JcaPKCS12SafeBagBuilder;
import org.bouncycastle.pkcs.jcajce.JcePKCS12MacCalculatorBuilder;
import org.bouncycastle.pkcs.jcajce.JcePKCSPBEInputDecryptorProviderBuilder;
import org.bouncycastle.pkcs.jcajce.JcePKCSPBEOutputEncryptorBuilder;
import org.bouncycastle.util.io.Streams;
import org.bouncycastle.util.io.pem.PemObject;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

public class JcePKCS12Example
{
    public static void main(String[] args)
            throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());




        //genSelfSignedCert();
        System.exit(0);

        KeyStore credentials = JcaUtils.createCredentials();
        PrivateKey key = (PrivateKey)credentials.getKey(JcaUtils.END_ENTITY_ALIAS, JcaUtils.KEY_PASSWD);
        Certificate[] chain = credentials.getCertificateChain(JcaUtils.END_ENTITY_ALIAS);

        createPKCS12File(new FileOutputStream("id.p12"), key, chain);

        //
        // first do a "blow by blow" read of the PKCS#12 file.
        //
        PKCS12PfxPdu pfx = readPKCS12File(new FileInputStream("id.p12"));

        //
        // or alternately just load it up using a KeyStore
        //
        KeyStore pkcs12Store = KeyStore.getInstance("PKCS12", "BC");

        pkcs12Store.load(new FileInputStream("id.p12"), JcaUtils.KEY_PASSWD);

        System.out.println("########## KeyStore Dump");

        for (Enumeration en = pkcs12Store.aliases(); en.hasMoreElements();)
        {
            String alias = (String)en.nextElement();

            if (pkcs12Store.isCertificateEntry(alias))
            {
                System.out.println("Certificate Entry: " + alias + ", Subject: " + (((X509Certificate)pkcs12Store.getCertificate(alias)).getSubjectDN()));
            }
            else if (pkcs12Store.isKeyEntry(alias))
            {
                System.out.println("Key Entry: " + alias + ", Subject: " + (((X509Certificate)pkcs12Store.getCertificate(alias)).getSubjectDN()));
            }
        }

        System.out.println();
    }

/*
    // needs reformat / code
    //
    public static void savePirvKeyToFile_andReadAgain() {

        // write to the file
        //
        java.security.Security.addProvider(org.bouncycastle.jce.provider.BouncyCastleProvider());
        keyGen = java.security.KeyPairGenerator.getInstance('RSA', 'BC');
        keyGen.initialize(2048,  java.security.SecureRandom());
        keypair = keyGen.generateKeyPair();
        privateKey = keypair.getPrivate();
        builder=org.bouncycastle.pkcs.jcajce.JcaPKCS8EncryptedPrivateKeyInfoBuilder(privateKey);

        m=org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC;
        encryptorBuilder = org.bouncycastle.pkcs.jcajce.JcePKCSPBEOutputEncryptorBuilder(m);
        password = 'test';
        outputBuilder = encryptorBuilder.build(password);
        privKeyObj = builder.build(outputBuilder);
        fos = java.io.FileOutputStream('testkey.pk8');
        fos.write(privKeyObj.getEncoded());
        fos.flush();
        fos.close();

        // read it back
        //
        myPath = java.nio.file.Paths.get(pwd,'testkey.pk8');
        encodedKey = java.nio.file.Files.readAllBytes(myPath);
        privKeyObj =org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo(encodedKey);
        cp=org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter();
        cp.setProvider('BC');

        decryptorBuilder = org.bouncycastle.pkcs.jcajce.JcePKCSPBEInputDecryptorProviderBuilder();
        inputBuilder = decryptorBuilder.build(password);
        info = privKeyObj.decryptPrivateKeyInfo(inputBuilder);
        decodedKey=cp.getPrivateKey(info);
    }
*/


/*
    // not working
    //
    public static void createPrivKeyPEM() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        keygen.initialize(2048);
        KeyPair keypair = keygen.generateKeyPair();

        PrivateKey privKey = keypair.getPrivate();

        PKCS8Generator pkcs8 = new PKCS8Generator(privKey, PKCS8Generator.AES_256_CBC, "BC");

        // PKCS8Generator(java.security.PrivateKey key, java.lang.String algorithm, java.lang.String provider)
        PKCS8Generator encryptorBuilder = new PKCS8Generator(privKey);
        encryptorBuilder.setPassword("testing".toCharArray());

        JcaPEMWriter writer = new JcaPEMWriter(new FileWriter(new File("pk.pem")));
        PemObject obj = encryptorBuilder.generate();

        writer.writeObject(obj);
        writer.flush();
        writer.close();
    }
*/


    public static void genSelfSignedCert() throws GeneralSecurityException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        JcePKCS12Example example = new JcePKCS12Example();
        String distinguishedName = "CN=Test, L=London, C=GB";
        Certificate certificate = example.generateCertificate(distinguishedName, keyPair, 365, "SHA256withRSA");
        System.out.println("it worked!");
    }

    /**
     * Create a self-signed X.509 Example
     *
     * @param dn        the X.509 Distinguished Name, eg "CN=Test, L=London, C=GB"
     * @param pair      the KeyPair
     * @param days      how many days from now the Example is valid for
     * @param algorithm the signing algorithm, eg "SHA1withRSA"
     */
    public X509Certificate generateCertificate(String dn, KeyPair pair, int days, String algorithm)
            throws GeneralSecurityException, IOException {
        PrivateKey privkey = pair.getPrivate();
        X509CertInfo info = new X509CertInfo();
        Date from = new Date();
        Date to = new Date(from.getTime() + days * 86400000l);
        CertificateValidity interval = new CertificateValidity(from, to);
        BigInteger sn = new BigInteger(64, new SecureRandom());
        X500Name owner = new X500Name(dn);

        info.set(X509CertInfo.VALIDITY, interval);
        info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
        info.set(X509CertInfo.SUBJECT, owner);
        info.set(X509CertInfo.ISSUER, owner);
        info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
        info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
        AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
        info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

        // Sign the cert to identify the algorithm that's used.
        X509CertImpl cert = new X509CertImpl(info);
        cert.sign(privkey, algorithm);

        // Update the algorith, and resign.
        algo = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
        info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
        cert = new X509CertImpl(info);
        cert.sign(privkey, algorithm);
        return cert;
    }



    private static void createPKCS12File(OutputStream pfxOut, PrivateKey key, Certificate[] chain)
            throws Exception
    {
        OutputEncryptor encOut = new JcePKCSPBEOutputEncryptorBuilder(NISTObjectIdentifiers.id_aes256_CBC).setProvider("BC").build(JcaUtils.KEY_PASSWD);

        PKCS12SafeBagBuilder taCertBagBuilder = new JcaPKCS12SafeBagBuilder((X509Certificate)chain[2]);

        taCertBagBuilder.addBagAttribute(PKCS12SafeBag.friendlyNameAttribute, new DERBMPString("Bouncy Primary Certificate"));

        PKCS12SafeBagBuilder caCertBagBuilder = new JcaPKCS12SafeBagBuilder((X509Certificate)chain[1]);

        caCertBagBuilder.addBagAttribute(PKCS12SafeBag.friendlyNameAttribute, new DERBMPString("Bouncy Intermediate Certificate"));

        JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
        PKCS12SafeBagBuilder eeCertBagBuilder = new JcaPKCS12SafeBagBuilder((X509Certificate)chain[0]);

        eeCertBagBuilder.addBagAttribute(PKCS12SafeBag.friendlyNameAttribute, new DERBMPString("Eric's Key"));
        SubjectKeyIdentifier pubKeyId = extUtils.createSubjectKeyIdentifier(chain[0].getPublicKey());
        eeCertBagBuilder.addBagAttribute(PKCS12SafeBag.localKeyIdAttribute, pubKeyId);

        PKCS12SafeBagBuilder keyBagBuilder = new JcaPKCS12SafeBagBuilder(key, encOut);

        keyBagBuilder.addBagAttribute(PKCS12SafeBag.friendlyNameAttribute, new DERBMPString("Eric's Key"));
        keyBagBuilder.addBagAttribute(PKCS12SafeBag.localKeyIdAttribute, pubKeyId);

        PKCS12PfxPduBuilder builder = new PKCS12PfxPduBuilder();

        builder.addData(keyBagBuilder.build());

        builder.addEncryptedData(new JcePKCSPBEOutputEncryptorBuilder(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC).setProvider("BC").build(JcaUtils.KEY_PASSWD), new PKCS12SafeBag[]{eeCertBagBuilder.build(), caCertBagBuilder.build(), taCertBagBuilder.build()});

        PKCS12PfxPdu pfx = builder.build(new JcePKCS12MacCalculatorBuilder(NISTObjectIdentifiers.id_sha256), JcaUtils.KEY_PASSWD);

        // make sure we don't include indefinite length encoding
        pfxOut.write(pfx.getEncoded(ASN1Encoding.DL));

        pfxOut.close();
    }

    private static PKCS12PfxPdu readPKCS12File(InputStream pfxIn)
            throws Exception
    {
        PKCS12PfxPdu pfx = new PKCS12PfxPdu(Streams.readAll(pfxIn));

        if (!pfx.isMacValid(new BcPKCS12MacCalculatorBuilderProvider(BcDefaultDigestProvider.INSTANCE), JcaUtils.KEY_PASSWD))
        {
            System.err.println("PKCS#12 MAC test failed!");
        }

        ContentInfo[] infos = pfx.getContentInfos();

        Map certMap = new HashMap();
        Map certKeyIds = new HashMap();
        Map privKeyMap = new HashMap();
        Map privKeyIds = new HashMap();

        InputDecryptorProvider inputDecryptorProvider = new JcePKCSPBEInputDecryptorProviderBuilder()
                .setProvider("BC").build(JcaUtils.KEY_PASSWD);
        JcaX509CertificateConverter  jcaConverter = new JcaX509CertificateConverter().setProvider("BC");

        for (int i = 0; i != infos.length; i++)
        {
            if (infos[i].getContentType().equals(PKCSObjectIdentifiers.encryptedData))
            {
                PKCS12SafeBagFactory dataFact = new PKCS12SafeBagFactory(infos[i], inputDecryptorProvider);

                PKCS12SafeBag[] bags = dataFact.getSafeBags();

                for (int b = 0; b != bags.length; b++)
                {
                    PKCS12SafeBag bag = bags[b];

                    X509CertificateHolder certHldr = (X509CertificateHolder)bag.getBagValue();
                    X509Certificate       cert = jcaConverter.getCertificate(certHldr);

                    Attribute[] attributes = bag.getAttributes();
                    for (int a = 0; a != attributes.length; a++)
                    {
                        Attribute attr = attributes[a];

                        if (attr.getAttrType().equals(PKCS12SafeBag.friendlyNameAttribute))
                        {
                            certMap.put(((DERBMPString)attr.getAttributeValues()[0]).getString(), cert);
                        }
                        else if (attr.getAttrType().equals(PKCS12SafeBag.localKeyIdAttribute))
                        {
                            certKeyIds.put(attr.getAttributeValues()[0], cert);
                        }
                    }
                }
            }
            else
            {
                PKCS12SafeBagFactory dataFact = new PKCS12SafeBagFactory(infos[i]);

                PKCS12SafeBag[] bags = dataFact.getSafeBags();

                PKCS8EncryptedPrivateKeyInfo encInfo = (PKCS8EncryptedPrivateKeyInfo)bags[0].getBagValue();
                PrivateKeyInfo info = encInfo.decryptPrivateKeyInfo(inputDecryptorProvider);

                KeyFactory keyFact = KeyFactory .getInstance(info.getPrivateKeyAlgorithm().getAlgorithm().getId(), "BC");
                PrivateKey privKey = keyFact.generatePrivate(new PKCS8EncodedKeySpec(info.getEncoded()));

                Attribute[] attributes = bags[0].getAttributes();
                for (int a = 0; a != attributes.length; a++)
                {
                    Attribute attr = attributes[a];

                    if (attr.getAttrType().equals(PKCS12SafeBag.friendlyNameAttribute))
                    {
                        privKeyMap.put(((DERBMPString)attr.getAttributeValues()[0]).getString(), privKey);
                    }
                    else if (attr.getAttrType().equals(PKCS12SafeBag.localKeyIdAttribute))
                    {
                        privKeyIds.put(privKey, attr.getAttributeValues()[0]);
                    }
                }
            }
        }

        System.out.println("########## PFX Dump");
        for (Iterator it = privKeyMap.keySet().iterator(); it.hasNext();)
        {
            String alias = (String)it.next();

            System.out.println("Key Entry: " + alias + ", Subject: " + (((X509Certificate)certKeyIds.get(privKeyIds.get(privKeyMap.get(alias)))).getSubjectDN()));
        }

        for (Iterator it = certMap.keySet().iterator(); it.hasNext();)
        {
            String alias = (String)it.next();

            System.out.println("Certificate Entry: " + alias + ", Subject: " + (((X509Certificate)certMap.get(alias)).getSubjectDN()));
        }
        System.out.println();

        return pfx;
    }
}
