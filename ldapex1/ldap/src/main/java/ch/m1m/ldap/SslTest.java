package ch.m1m.ldap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;

public class SslTest {

    public static void main(String... args) {
        new SslTest().testIt();
    }

    private void testIt() {

        Void v = doNothing();

        //
        // https://console-openshift-console.apps.ocp4-test.sympany-test.ads/
        // https://www.google.com/
        //
        String httpsUrl = "https://www.google.com/";
        //String httpsUrl = "https://console-openshift-console.apps.ocp4-test.sympany-test.ads/";
        URL url;
        try {
            url = new URL(httpsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            //dump all cert info
            printHttpsCert(con);

            //dump all the content
            printContent(con);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Void doNothing() {
        //return Void.TYPE.newInstance();
        return Void.TYPE.cast(null);
    }

    private void printHttpsCert(HttpsURLConnection con) {
        if (con != null) {
            try {
                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for (java.security.cert.Certificate cert : certs) {
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void printContent(HttpsURLConnection con) {
        if (con != null) {
            try {
                System.out.println("** ** ** ** ** **  Content of the URL ** ** ** ** ** ** ** ** ");
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String input;

                while ((input = br.readLine()) != null) {
                    System.out.println(input);
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

/*


$ oc create configmap jenkins-trusted-ca-bundle -n <namespace>
$ oc label configmap jenkins-trusted-ca-bundle config.openshift.io/inject-trusted-cabundle=true -n <namespace>
$ oc set volume --add dc/jenkins --type=configmap --configmap-name=jenkins-trusted-ca-bundle  --name=jenkins-trusted-ca-bundle --mount-path=/etc/pki/ca-trust/source/anchors -n <namespace>


echo | \
    openssl s_client -servername console-openshift-console.apps.ocp4-test.sympany-test.ads -connect console-openshift-console.apps.ocp4-test.sympany-test.ads:443 2>/dev/null | \
    openssl x509 -text

openssl x509 -in ocp4cert.pem -text


-Djavax.net.ssl.trustStore=/Users/mue/work/git/sympany/soang/service-proxy/sympanyCAcerts.jks
-Djavax.net.ssl.trustStorePassword=changed
-Djavax.net.ssl.trustStoreType=jks


keytool -import -file ocp4cert.pem -alias apps.ocp4-test.sympany-test.ads -keystore ocpTrust.jks
(password: testtest)

keytool -import -file sympanyRootCATest.pem -alias sympanyTestRootCA -keystore sympanyCAcerts.jks
(password: changed)

keytool -list -v -storepass changeit -keystore sympanyCAcerts.jks

oc create configmap sympany-root-ca --from-file=/Users/mue/work/git/sympany/soang/service-proxy/sympanyCAcerts.jks

container: /etc/pki/ca-trust/extracted/java/cacerts

# ocp4 test api server cert
-----BEGIN CERTIFICATE-----
MIIFbTCCA1WgAwIBAgIJAIjWH3YkN1owMA0GCSqGSIb3DQEBCwUAME0xEzARBgoJ
kiaJk/IsZAEZFgNhZHMxHDAaBgoJkiaJk/IsZAEZFgxzeW1wYW55LXRlc3QxGDAW
BgNVBAMTD1N5bXBhbnkgUm9vdCBDQTAeFw0yMDA3MjExMDAzMDBaFw0yMjA3MjEx
MDAzMDBaMEwxCzAJBgNVBAYTAkNIMRMwEQYDVQQKDApTeW1wYW55IEFHMSgwJgYD
VQQDDB9hcHBzLm9jcDQtdGVzdC5zeW1wYW55LXRlc3QuYWRzMIICIjANBgkqhkiG
9w0BAQEFAAOCAg8AMIICCgKCAgEA29CDVoqx2D63dH7CAHcklUEmbrNQ8cAvi9c4
hF9f3BQBAAArHirmQX9cg3pOHRiqkx8cdNvwH06MXC7Rwq5bw51p3JIUXN+7AGVB
4lHadeTb+syToc29biYqJbVNuWoDY6yUVv+iyDbWDlo3wUiimrTLRsO6QjS2Jzjs
5lnqlA7cm45p6LFR17ZagAKjV137PJ5KXdj1c2qabwasLgUmD25I/UydMON4C6Kk
vzoQe6ISwNqO3NsDjkivDnt0KNC9wdhvtbR8KnDUU/2WYStl7lzCo+Txc0GmO55J
uWHRJ96s6vnYrF2LcOV+A7TgqtbQfwfUHu4JYLicTCPupyMM8dBJHN0CRO0GHMqf
LWrKs4b+rkVJqRL//dekiYn5PkO864U5S6HnnIVLHXK2mxZNEhRqYnrIcD7LAISW
quRbobydpfyHnUyNQmX0CcQjyBm4OL756TILXIAAg99XFvZ+Y7FH4n2epH3f32Kh
EUQIoJV2JTfK2fhdrME+tsjEdXWeRnKG2dz8a85la4mwCCl6JiF44YLgH77+YntT
vfcD7VBCVaYK4sIKU90pTvcvDbwUjKM2qrkoQdolq40KkiGIHGUI4DuzpSYNxFK3
HWB10DYB0AIHpQbg7xyE55H3O74cS8a5XrH3P5gngMzQFgdQdNm6pc6/ftj2IRbg
DWJYgHcCAwEAAaNRME8wTQYDVR0RBEYwRIIfYXBwcy5vY3A0LXRlc3Quc3ltcGFu
eS10ZXN0LmFkc4IhKi5hcHBzLm9jcDQtdGVzdC5zeW1wYW55LXRlc3QuYWRzMA0G
CSqGSIb3DQEBCwUAA4ICAQDH/qajM22TRSXfKNkq4hCKcpOQf+0jA/0tdS+wkEz8
p+fy4zsqaY38p3c0YF+1fEuGuc0L8FytXTdd3fiRE0HuROpLFnmWEEbkzear1Ogw
7JHkgBMS0BFIHOHHI4D6j+3aseZPRb/frBrLLw+NbL1PXdkhMdbetYgQE0KU/wtr
lXvHkYbwn4jeBYUtmBIjc1imODtmDSLEyDAkPioAdxPkSbGVoWL8Y3uf/HsHaKf4
vWMqyHmRaMWR/XOfQFmluDvumTazwGeb3m9TIjKWUD7TozA4VgM/cK70Q564L1Dw
akyU37oMc39ILQ7mrknN7XCW6Ada0RGXKgOIU/2H0Bxfpf7ba63K2XPSK2Ul94Nf
+zHZ5vfSnygzD6xoMyA73zwIi55JLrQIMKUmcV+pdtO8DfFonOLO7xUK268WBiPY
j8+1E49UEQ6ynriavaSYFYNPW6QVYR9S8kbUH67rYHnuFCWDyfSsvOwUb85c/TWb
1MoYf5BzIZIwiTTlI/6+UoLw8Y45ZPMwV2l/VmdB7wNLmJUdx0FZGUovLV0JXWaU
dCjA28vBtUUTbHC1+30ZOEOXI8P66zOMxYUzUcw8PTfVrTUPSOM5JJU3vHy9Nv7P
hphOIc0qlv6jLUiLC0kAQga2cOB8yHGo58/K9rxU1mfP/PccWSNjCi9uv05h/DXE
yw==
-----END CERTIFICATE-----

# sympany test CA
-----BEGIN CERTIFICATE-----
MIIFnDCCA4SgAwIBAgIQHpJ7gHigebtH5v6ZA0focjANBgkqhkiG9w0BAQsFADBN
MRMwEQYKCZImiZPyLGQBGRYDYWRzMRwwGgYKCZImiZPyLGQBGRYMc3ltcGFueS10
ZXN0MRgwFgYDVQQDEw9TeW1wYW55IFJvb3QgQ0EwHhcNMTgxMjExMDk1OTE5WhcN
MjgxMjExMTAwOTE5WjBNMRMwEQYKCZImiZPyLGQBGRYDYWRzMRwwGgYKCZImiZPy
LGQBGRYMc3ltcGFueS10ZXN0MRgwFgYDVQQDEw9TeW1wYW55IFJvb3QgQ0EwggIi
MA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQDSTZMio84lWsDZ5VIfECPMvtWf
9tpJcp98yID+HSLkj+FZDcGRoK0lovBQqPMaWwcBwsIx/Fx1plEs2TKVCC2R7JMI
IFeeE9kOi7I7rYSCNjVcLV8nIgs/7Gv3u7Gn4gTKY4nf3w7IPYMa8uwQLGWi+DdJ
6XvwU8uI6fCBfDOKxobl3nBJbogEd9uxTneZ+BRNRas33N17/ilfSBJJz0udwinb
etBjirRnmliGNgDUt3h7AJZxDQR3W6b64htk42EpY25m6Egv7DFFzuZoNerZapdR
FKwPen2j6EG8xX1EIsTZZMTfEc7tL8vuT99CbiShiUfzCU7Z+A/TW53op06oowqx
h2WItW6Q8yMop/f2qNo4MjyB2g7mLDYDNamYldAUJyJouXpe7uRPJxM6GmxrDlWQ
0PxEpLinmF22gjhttl0wrVk02i8eoHi5p1pN+Z0lpzQMrEBhqRLH4imn98tnnoLh
BkZDE1i46WrozH3NORowV1ze4R0xxWyRsl3TvISfRkv61nBQGtORGGW18IiHFi6p
DvADcYfJ0s3Od1GCyE/zutCNYY/cf/Vuz0C2y/NDWqsjUluEhlzvdvn0/eCQnHDt
zAhRlauWbLTGfyGToCSNA1Md8jQnfKcx8iq2c0/Ncm+duNZjKnBd9DGsydjA8u6E
kFprnWqHk3JUOnFoEwIDAQABo3gwdjALBgNVHQ8EBAMCAYYwDwYDVR0TAQH/BAUw
AwEB/zAdBgNVHQ4EFgQUQeGvtIK/a2er6vGad9Hr2csJLv4wEgYJKwYBBAGCNxUB
BAUCAwMAAzAjBgkrBgEEAYI3FQIEFgQUli1m/sDBkYf5AhfQcDX78bmO+SswDQYJ
KoZIhvcNAQELBQADggIBAAbJWFdscP1UReYGQqAH51tLLWi/q0aolQ3KH62ac9UQ
ZPsRmGGM+GKwW06htNrtLxCxYTMfomMpgG7GdCqe96XrcAXfj3mG2GECR05LtKZ3
ZbEJ83Touw70MZR00wqZCc1VJ+dxC/DdPGPveCSH/Fe6vCP/vN/OUygDqP7xND6J
jVYWhopTqeY8mcz3LRnOqX/ODMFWS/2KCqThgCxbQgxYCMwF1KzSefd11bMdL+iF
3yRlscTmEgr7j7d3Ecyg03R/FlzvWh1MG5QmUtnA3eMlXr9DwTUDFYaLdS+5w/0H
SNXT7Kf+Gu4Pqrp4uD/fcmgz3e4ccmkQOrxcEdrk0iNra7ZVLgrficlWmxyv2/i8
Y2xWksawTCkqITR2PoT9iaSbJUYqEv5FPVzjMRZDdOlcSsQuMzjnDoUAbgRkdwa8
GOofjeKCkyCcOHXVXb25YAATv2L9HG8Rg1vHTOCN5HH7HvMXq/uo6pnVlqwD0v73
fgw/Adxt1xvHX/uVLGJQoJhJka8H37SZHwXASZg4xW6n++h/JO41BVQTT87N9aB0
fi2SUFGK0tctAez5PCGBepJJ1p884VRyXdsh9dn2GQNex/qIJQJRCIDg5A713kyi
qvvt2R4mad+bPeVlb1cThBRbYfSyeZZPLiSIJIVXrhWcHTnxeUwbKx8NA7X0cQZQ
-----END CERTIFICATE-----

 */