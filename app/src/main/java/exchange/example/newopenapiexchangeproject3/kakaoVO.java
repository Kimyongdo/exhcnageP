package exchange.example.newopenapiexchangeproject3;

import java.io.Serializable;

public class kakaoVO implements Serializable {
    private String loginNickname;
    private String loginNickimage;
    private int logintnumber;
    private String logoutNickname;
    private int logoutNickimage;
    private int logoutnumber;
    private long nicknumber;

    public kakaoVO(String loginNickname, String loginNickimage, int logintnumber, String logoutNickname, int logoutNickimage, int logoutnumber, long nicknumber) {
        this.loginNickname = loginNickname;
        this.loginNickimage = loginNickimage;
        this.logintnumber = logintnumber;
        this.logoutNickname = logoutNickname;
        this.logoutNickimage = logoutNickimage;
        this.logoutnumber = logoutnumber;
        this.nicknumber = nicknumber;
    }

    public String getLoginNickname() {
        return loginNickname;
    }

    public void setLoginNickname(String loginNickname) {
        this.loginNickname = loginNickname;
    }

    public String getLoginNickimage() {
        return loginNickimage;
    }

    public void setLoginNickimage(String loginNickimage) {
        this.loginNickimage = loginNickimage;
    }

    public int getLogintnumber() {
        return logintnumber;
    }

    public void setLogintnumber(int logintnumber) {
        this.logintnumber = logintnumber;
    }

    public String getLogoutNickname() {
        return logoutNickname;
    }

    public void setLogoutNickname(String logoutNickname) {
        this.logoutNickname = logoutNickname;
    }

    public int getLogoutNickimage() {
        return logoutNickimage;
    }

    public void setLogoutNickimage(int logoutNickimage) {
        this.logoutNickimage = logoutNickimage;
    }

    public int getLogoutnumber() {
        return logoutnumber;
    }

    public void setLogoutnumber(int logoutnumber) {
        this.logoutnumber = logoutnumber;
    }

    public long getNicknumber() {
        return nicknumber;
    }

    public void setNicknumber(long nicknumber) {
        this.nicknumber = nicknumber;
    }
}
