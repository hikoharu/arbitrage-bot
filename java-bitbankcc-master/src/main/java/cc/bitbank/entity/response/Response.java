package cc.bitbank.entity.response;

import cc.bitbank.entity.Data;

/**
 * Created by tanaka on 2017/04/11.
 */
public class Response<T extends Data> {
    public int success;
    public T data;

    @Override
    public String toString() {
        return "[Response] success " + success + ", data " + data;
    }
}
