/*
 
https://jwt.io/

EC Crypto

parts...

curve field q
curve a
curve b

spec  G
spec  n
spec  h  (1)

key public  Q

key private d


https://github.com/jwtk/jjwt

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.9.1</version>
		</dependency>
 
*/

package ch.m1m.jwt;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.EllipticCurveProvider;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class JwtMainWebToken {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) {

        try {
            exampleECSigProvider();

            //exampleNoneProvider();
            //exampleMacProvider();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private static void exampleECSigProvider() {

        //SignatureAlgorithm sigAlgo = SignatureAlgorithm.ES512;
        SignatureAlgorithm sigAlgo = SignatureAlgorithm.ES256;

        System.out.println("EllipticCurve Signature provider: running...");
        String claimId = UUID.randomUUID().toString();

        KeyPair keyPair = EllipticCurveProvider.generateKeyPair(sigAlgo);
        System.out.println("KeyPair: " + keyPair.toString());

        //PrivateKey genPrivKey = pubPrivKeys.getPrivate();
        //ECPrivateKeySpec privKey = genPrivKey.

        ECPublicKey ecPubKey = (ECPublicKey)keyPair.getPublic();
        ECPoint ecPubPointW = ecPubKey.getW();
        System.out.println("pub  key: " + ecPubKey.toString());

        ECPrivateKey ecPrivKey = (ECPrivateKey)keyPair.getPrivate();
        BigInteger ecPrivS = ecPrivKey.getS();
        System.out.println("priv key: " + ecPrivKey.toString());
        System.out.println("S: " + ecPrivS.toString(16));

        Map<String, Object> customClaims = new HashMap<String, Object>();
        customClaims.put("sym_pkref", "sig_publ_key_88");

        // build the token
        // note: first set the custom map, then overwrite the standard fields
        //
        String token1 = Jwts.builder()
                .setHeaderParam("kid", "kid-NotSet")
                .setClaims(customClaims)
                .setSubject("Joe")
                .setId(claimId)
                .signWith(sigAlgo, keyPair.getPrivate())
                .compact();

        System.out.println("generated token: " + token1);

        // parse the token
        //

        try {
			/*

		    Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws1);

		    Jws<Claims> claims = Jwts.parser()
		            .setSigningKey(key)
		            .parseClaimsJws(token1);
		    */

            Jwt<JwsHeader, Claims> jwt = Jwts.parser()
                    .setSigningKey(keyPair.getPublic())
                    .parseClaimsJws(token1);

            System.out.println("token header: " + jwt.getHeader().toString());
            System.out.println("token body:   " + jwt.getBody().toString());

            String tokenSubject = jwt.getBody().getSubject();
            String tokenPubKeyRef = (String) jwt.getBody().get("sym_pkref");

            System.out.println("subject: " + tokenSubject);
            System.out.println("sym_pkref: " + tokenPubKeyRef);

            //OK, we can trust this JWT

        } catch (JwtException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void exampleNoneProvider() {

        System.out.println("None provider: running...");

        Map<String, Object> customClaims = new HashMap<String, Object>();
        customClaims.put("sym_pkref", "no-key-involved");

        // build the token
        // note: first set the custom map, then overwrite the standard fields
        //
        String token1 = Jwts.builder()
                .setClaims(customClaims)
                .setSubject("Joe")
                //.signWith(SignatureAlgorithm.ES256, pubPrivKeys.getPrivate())
                .compact();

        System.out.println("generated token: " + token1);

        // parse the token
        //

        try {
			/*
			
		    Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws1);
		    		    
		    Jws<Claims> claims = Jwts.parser()
		            .setSigningKey(key)
		            .parseClaimsJws(token1);
		    */

            Jwt<JwsHeader, Claims> jwt = Jwts.parser()
//		            .setSigningKey(pubPrivKeys.getPublic())
                    .parseClaimsJws(token1);

            String tokenSubject = jwt.getBody().getSubject();
            String tokenPubKeyRef = (String) jwt.getBody().get("sym_pkref");

            System.out.println("subject: " + tokenSubject);
            System.out.println("sym_pkref: " + tokenPubKeyRef);

            //OK, we can trust this JWT

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void exampleMacProvider() {

        System.out.println("MAC provider: running...");

        Key key1 = MacProvider.generateKey();
        Key key2 = MacProvider.generateKey();

        byte[] encodedKey = "thisismynotsosecretkey".getBytes();
        SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        Key key3 = originalKey;
        key1 = key3;

        Map<String, Object> customClaims = new HashMap<String, Object>();
        customClaims.put("sym_pkref", "sig_publ_key_88");

        // build the token
        // note: first set the custom map, then overwrite the standard fields
        //
        String token1 = Jwts.builder()
                .setClaims(customClaims)
                .setSubject("Joe")
                .signWith(SignatureAlgorithm.HS512, key1)
                .compact();

        System.out.println("generated token: " + token1);

        // parse the token
        //

        try {
			/*
			
		    Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws1);
		    		    
		    Jws<Claims> claims = Jwts.parser()
		            .setSigningKey(key)
		            .parseClaimsJws(token1);
		    */

            Jwt<JwsHeader, Claims> jwt = Jwts.parser()
                    .setSigningKey(key1)
                    .parseClaimsJws(token1);

            String tokenSubject = jwt.getBody().getSubject();
            String tokenPubKeyRef = (String) jwt.getBody().get("sym_pkref");

            System.out.println("subject: " + tokenSubject);
            System.out.println("sym_pkref: " + tokenPubKeyRef);

            //OK, we can trust this JWT

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
