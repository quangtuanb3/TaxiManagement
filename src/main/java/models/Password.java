package models;

import java.io.Serializable;

public class Password  implements Serializable {
    private byte[] key;
    private String passcode;

    public Password(String password) {
        this.passcode = password;
    }

    public Password(byte[] key, String password) {
        this.key = key;
        this.passcode = password;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
