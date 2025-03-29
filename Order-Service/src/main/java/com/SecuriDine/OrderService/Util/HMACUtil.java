package com.SecuriDine.OrderService.Util;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HMACUtil {
    private static final String HMAC_ALGO = "HmacSHA256";
    //private static final byte[] SECRET_KEY = "SecureSeedNum12321".getBytes(); // Remove hardcode and store in secrets instead

    public static String generateHMAC(String data) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGO);
        SecretKey secretKey = getSecretKey();
        mac.init(secretKey);
        byte[] hmacBytes = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hmacBytes);
    }

    public static boolean verifyHMAC(String data, String storedHMAC) throws Exception {
        String newHMAC = generateHMAC(data);
        return newHMAC.equals(storedHMAC);
    }
    
    private static SecretKey getSecretKey() throws Exception {
        String key = SecretManagerUtil.getHMACKey();
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
    }
}

