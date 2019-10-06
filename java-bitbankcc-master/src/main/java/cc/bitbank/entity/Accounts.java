package cc.bitbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by tanaka on 2017/04/13.
 */
public class Accounts extends Data {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Account {
        public String uuid;
        public String label;
        public String address;

        public String toString() {
            return "[Account] uuid " + uuid + ", label " + label + ", address " + address;
        }
    }

    public Account[] accounts;

    public Accounts() {}
    public Accounts(Account[] a) {
        this.accounts = a;
    }
}
