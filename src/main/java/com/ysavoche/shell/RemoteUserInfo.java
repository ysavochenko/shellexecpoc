package com.ysavoche.shell;

import com.jcraft.jsch.UserInfo;

public class RemoteUserInfo implements UserInfo {

    private final String username;
    private final String password;

    public RemoteUserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassphrase() {
        throw new UnsupportedOperationException("getPassphrase Not supported yet.");
    }

    public String getPassword() {
        return this.password;
    }

    public boolean promptPassword(String s) {
        return true;
    }

    public boolean promptPassphrase(String s) {
        throw new UnsupportedOperationException("promptPassphrase Not supported yet.");
    }

    public boolean promptYesNo(String s) {
        return true;
    }

    public void showMessage(String s) {

    }
}
