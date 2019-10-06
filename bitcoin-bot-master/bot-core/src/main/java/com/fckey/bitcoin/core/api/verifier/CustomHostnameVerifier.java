package com.fckey.bitcoin.core.api.verifier;


import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Created by fckey on 2016/04/05.
 */

public class CustomHostnameVerifier implements X509HostnameVerifier {
    private BrowserCompatHostnameVerifier mHostnameVerifier;
    private Map<String, String> mHostMap;

    public CustomHostnameVerifier(Map<String, String> hostMap) {
        mHostnameVerifier = new BrowserCompatHostnameVerifier();
        mHostMap = hostMap;
    }

    @Override
    public boolean verify(String host, SSLSession session) {
        if (mHostMap.keySet().contains(host)) {
            return true;
        }
        return mHostnameVerifier.verify(getCustomHost(host), session);
    }

    @Override
    public void verify(String host, SSLSocket ssl) throws IOException {
        mHostnameVerifier.verify(getCustomHost(host), ssl);
    }

    @Override
    public void verify(String host, X509Certificate cert) throws SSLException {
        mHostnameVerifier.verify(getCustomHost(host), cert);
    }

    @Override
    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
        mHostnameVerifier.verify(getCustomHost(host), cns, subjectAlts);
    }

    private String getCustomHost(String originalHost) {
        if (!mHostMap.containsKey(originalHost)) {
            return originalHost;
        }

        return mHostMap.get(originalHost);
    }
}
