package cc.bitbank.exception;

/**
 * Created by tanaka on 2017/04/11.
 */
public class BitbankException extends Exception {

    public int code;

    public BitbankException() {
        super();
        this.code = 0;
    }

    public BitbankException(int code) {
        this.code = code;
    }

    public BitbankException(String message) {
        super(message);
    }
}
