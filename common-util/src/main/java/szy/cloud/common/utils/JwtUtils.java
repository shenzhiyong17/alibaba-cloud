package szy.cloud.common.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.BaseEncoding;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.K;

public class JwtUtils {

    private static final String KEY = "5hbWUiOiJ0ZXMxIiwicHdkIj0L";
    private final static long JWT_EXPIRE_TIME = 8 * 3600 * 1000L;

    private static SecretKey generalKey() {
//        byte[] encodedKey = Base64.decodeBase64(KEY);
        byte[] encodedKey = BaseEncoding.base64().decode(KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "RSA");
    }

    public static String createJWT(String id, String issuer, Map<String, Object> claims, long ttlMillis, String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        SecretKey key = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static String createJWT(String username, String pwd, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userName", username);
        claims.put("pwd", pwd);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        System.out.println(" now :" + now);

        SecretKey key = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuer(username)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, key);
        System.out.println(" builder :" + builder);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static String createJWT(String id){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", id);
        SecretKey key = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, key);
        return builder.compact();
    }

    /**
     * 解密
     */
    public static Claims parseJWT(String jwt) throws Exception{
            return Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(jwt)
                    .getBody();
    }
}
