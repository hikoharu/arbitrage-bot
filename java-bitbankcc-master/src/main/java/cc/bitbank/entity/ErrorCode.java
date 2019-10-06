package cc.bitbank.entity;

/**
 * Created by tanaka on 2017/04/11.
 */
public class ErrorCode extends Data {
    public int code;

    @Override
    public String toString() {
        return "[Error] code " + code;
    }
}
