package com.fckey.bitcoin.core.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import javax.net.ssl.HostnameVerifier;

import com.fckey.bitcoin.core.api.header.RequestHeaderFactory;
import com.fckey.bitcoin.core.api.header.SimpleRequestHeaderFactory;
import com.fckey.bitcoin.core.api.verifier.CustomHostnameVerifier;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Created by fckey on 2016/04/05.
 */
public abstract class ApiClientBase {

    public static final String CONTENT_TYPE_JSON = "application/json";
    private RequestHeaderFactory requestHeaderFactory;

    public ApiClientBase() {
        this.requestHeaderFactory = new SimpleRequestHeaderFactory();
    }

    public ApiClientBase(RequestHeaderFactory requestHeaderFactory) {
        this.requestHeaderFactory = requestHeaderFactory;
    }

    private HostnameVerifier verifier = null;

    protected String requestGetByUrlSimple(String url) {
        return requestGetByUrlWithHeader(url, requestHeaderFactory.build());
    }

    protected String requestGetByUrlWithHeader(String url, Map<String, String> header) {
        HttpRequestFactory factory = getHttpRequestFactory(header);
        String jsonString;
        try {
            HttpRequest request = factory.buildGetRequest(new GenericUrl(url));
            HttpResponse response = request.execute();
            jsonString = response.parseAsString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonString = "";
        }
        return jsonString;
    }

    protected String requestPostByUrlWithHeader(String url, Map<String, String> header, String requestBody) throws IOException {
        HttpRequestFactory factory = getHttpRequestFactory(header);
        String jsonString;
        
            HttpContent content = ByteArrayContent.fromString(CONTENT_TYPE_JSON, requestBody);
            HttpRequest request = factory.buildPostRequest(new GenericUrl(url), content);
            request.getHeaders().setContentType(CONTENT_TYPE_JSON);
            HttpResponse response = request.execute();
            if (!HttpStatusCodes.isSuccess(response.getStatusCode())) {
                throw new IllegalStateException(String.format("Post for %s was not succeeded. Status message: %s", content, response.getStatusMessage()));
            }
            jsonString = response.parseAsString();
         
        return jsonString;
    }

    /**
     * Create factory of http request
     *
     * @return
     */
    protected HttpRequestFactory getHttpRequestFactory(Map<String, String> header) {
        NetHttpTransport transport = getNetHttpTransport();

        return transport.createRequestFactory(new HttpRequestInitializer() {
            public void initialize(final HttpRequest request) throws IOException {
                request.setConnectTimeout(0);
                request.setReadTimeout(0);
                request.setParser(new JacksonFactory().createJsonObjectParser());
                final HttpHeaders httpHeaders = new HttpHeaders();
                for (Map.Entry<String, String> e : header.entrySet()) {
                    httpHeaders.set(e.getKey(), e.getValue());
                }
                request.setHeaders(httpHeaders);
            }
        });
    }

    /**
     * Craete Net Http Transport with host name verifier
     *
     * @return
     */
    protected NetHttpTransport getNetHttpTransport() {
        //        ApacheHttpTransport transport = new ApacheHttpTransport();
        return new NetHttpTransport.Builder().setHostnameVerifier(
                getHostnameVerifier()
//                org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
        ).build();
    }

    /**
     * Create Hostname Verifier for SSL
     *
     * @return
     */
    protected CustomHostnameVerifier getHostnameVerifier() {
        return new CustomHostnameVerifier(new HashMap<String, String>() {{
            put("api.bitflyer.jp", "incapsula.com");
        }});
    }


    protected String genUrlParam(String params) {
        if (params.isEmpty()) {
            return "";
        }
        return String.format("?%s", params);
    }

    /**
     * Create params for GET URL for given map
     *
     * @param params - Key is param name and Value is the param value
     * @return
     */
    protected String genUrlParam(Map<String, String> params) {
        if (params.isEmpty()) {
            return "";
        }
        String urlParam = "?";
        StringJoiner joiner = new StringJoiner("&");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            joiner.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        urlParam += joiner.toString();
        return urlParam;
    }
}
