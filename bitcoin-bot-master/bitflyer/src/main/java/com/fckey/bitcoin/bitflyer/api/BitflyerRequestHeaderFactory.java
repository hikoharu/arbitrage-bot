package com.fckey.bitcoin.bitflyer.api;

import com.fckey.bitcoin.core.api.AccessParams;
import com.fckey.bitcoin.core.api.header.RequestHeaderElement;
import com.fckey.bitcoin.core.api.header.RequestHeaderFactory;
import com.fckey.bitcoin.core.api.header.RequestHeaderFactoryBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fckey on 2016/04/12.
 */
public class BitflyerRequestHeaderFactory extends RequestHeaderFactoryBase {

    public static final String ACCESS_KEY = "ACCESS-KEY";
    public static final String ACCESS_TIMESTAMP = "ACCESS-TIMESTAMP";
    public static final String ACCESS_SIGN = "ACCESS-SIGN";

    private static AccessParams accessParams;
    private static RequestHeaderElement element;
    private static BitflyerRequestHeaderFactory instance = null;
    private BitflyerRequestHeaderFactory() {
        super();

    }

    public static void init(AccessParams params) {
        accessParams = params;
    }

    public static BitflyerRequestHeaderFactory getInstance() {
        if (accessParams == null) {
            throw new IllegalStateException("Access keys are not initialized");
        }

        if (instance == null) {
            instance = new BitflyerRequestHeaderFactory();
        }
        element = null;
        return instance;
    }

    private String createSignature(String path, String method, String nonce, String body) {
        String message = nonce + method + path + body;
        return hmacSha256Encode(accessParams.getAccessSecret(), message);
    }

    public RequestHeaderFactory setElement(RequestHeaderElement element) {
        this.element = element;
        return this;
    }

    public static BitflyerRequestHeaderElement getElement() {
        return (BitflyerRequestHeaderElement)element;
    }

    @Override
    public Map<String, String> build() {
        if (element == null) {
            throw new IllegalStateException("Element is not initialized");
        }
        BitflyerRequestHeaderElement el= getElement();
        HashMap<String, String> header = new HashMap<>();
        header.put(ACCESS_KEY, accessParams.getAccessKey());
        String nonce = createNonce();
        header.put(ACCESS_TIMESTAMP, nonce);
        header.put(ACCESS_SIGN, createSignature(el.getPath(), el.getMethod(), nonce, el.getBody()));
        return header;
    }

    public static class BitflyerRequestHeaderElement implements RequestHeaderElement {
        private String path;
        private String method;
        private String body;

        public BitflyerRequestHeaderElement(String path, String method, String body) {
            this.path = path;
            this.method = method;
            this.body = body;
        }

        public String getPath() {
            return path;
        }

        public String getMethod() {
            return method;
        }

        public String getBody() {
            return body;
        }
    }

}
