package com.fckey.bitcoin.core.api.header;

import java.util.Map;

/**
 * Created by fckey on 2016/04/11.
 */
public interface RequestHeaderFactory {

    public RequestHeaderFactory setElement(RequestHeaderElement element);

    public Map<String, String > build();
}
