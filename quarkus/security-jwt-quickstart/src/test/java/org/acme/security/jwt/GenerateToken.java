package org.acme.security.jwt;

import java.util.HashMap;

import org.eclipse.microprofile.jwt.Claims;

/**
 * A simple utility class to generate and print a JWT token string to stdout. Can be run with:
 * mvn exec:java -Dexec.mainClass=org.acme.security.jwt.GenerateToken -Dexec.classpathScope=test
 */
public class GenerateToken {
    /**
     * @param args - [0]: optional name of classpath resource for json document of claims to add; defaults to "/JwtClaims.json"
     *             [1]: optional time in seconds for expiration of generated token; defaults to 300
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String claimsJson = "/JwtClaims.json";
        HashMap<String, Long> timeClaims = new HashMap<>();
        long exp = TokenUtils.currentTimeInSecs() + (24 * 3600);
        timeClaims.put(Claims.exp.name(), exp);
        String token = TokenUtils.generateTokenString(claimsJson, timeClaims);
        System.out.println(token);
    }
}
