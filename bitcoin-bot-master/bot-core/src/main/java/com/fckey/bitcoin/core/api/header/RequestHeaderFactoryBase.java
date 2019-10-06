package com.fckey.bitcoin.core.api.header;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by fckey on 2016/04/11.
 */
public abstract class RequestHeaderFactoryBase implements RequestHeaderFactory {

    public static final String HMAC_SHA_256 = "HmacSHA256";

    @Override
    public abstract Map<String, String> build();


    protected String createNonce() {
        long currentUnixTime = System.currentTimeMillis() / 1000L;
        String nonce = String.valueOf(currentUnixTime);
        return nonce;
    }

    public static String hmacSha256Encode(String secretKey, String message) {

        SecretKeySpec keySpec = new SecretKeySpec(
                secretKey.getBytes(),
                HMAC_SHA_256);

        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA_256);
            mac.init(keySpec);
        } catch (NoSuchAlgorithmException e) {
            // can't recover
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            // can't recover
            throw new RuntimeException(e);
        }
        byte[] rawHmac = mac.doFinal(message.getBytes());
        return Hex.encodeHexString(rawHmac);
    }
}
