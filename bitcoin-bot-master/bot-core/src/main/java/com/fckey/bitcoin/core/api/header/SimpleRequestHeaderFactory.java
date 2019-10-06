package com.fckey.bitcoin.core.api.header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fckey on 2016/04/14.
 */
public class SimpleRequestHeaderFactory extends RequestHeaderFactoryBase {
    @Override
    public RequestHeaderFactory setElement(RequestHeaderElement element) {
        return this;
    }

    @Override
    public Map<String, String> build() {
        return new HashMap<>();
    }
}
