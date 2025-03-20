package com.SecuriDine.OrderService.Util;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HMACUtil {
    private static final String HMAC_ALGO = "HmacSHA256";
    private static final byte[] SECRET_KEY = "SecureSeedNum12321".getBytes(); // Store securely!

    public static String generateHMAC(String data) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, HMAC_ALGO);
        mac.init(keySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hmacBytes);
    }

    public static boolean verifyHMAC(String data, String storedHMAC) throws Exception {
        String newHMAC = generateHMAC(data);
        return newHMAC.equals(storedHMAC);
    }
}

