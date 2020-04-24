package exchange.example.newopenapiexchangeproject3;

import java.io.Serializable;

public class Itemlist implements Serializable {
    private String cur_nm;
    private String cur_unit ;
    private String kftc_deal_bas_r;
    private int nationimage; //이미지는 int로 받는다.


    //뉴스시간설정
    private  String newstime;
    private String newstimeglobal;

    //날씨
    private String tdoayC1;
    private String todayConditon;
    private String timedifferent;

    public Itemlist(String cur_nm, String cur_unit, String kftc_deal_bas_r, int nationimage, String newstime, String newstimeglobal, String tdoayC1, String todayConditon, String timedifferent) {
        this.cur_nm = cur_nm;
        this.cur_unit = cur_unit;
        this.kftc_deal_bas_r = kftc_deal_bas_r;
        this.nationimage = nationimage;
        this.newstime = newstime;
        this.newstimeglobal = newstimeglobal;
        this.tdoayC1 = tdoayC1;
        this.todayConditon = todayConditon;
        this.timedifferent = timedifferent;
    }

    public String getCur_nm() {
        return cur_nm;
    }

    public void setCur_nm(String cur_nm) {
        this.cur_nm = cur_nm;
    }

    public String getCur_unit() {
        return cur_unit;
    }

    public void setCur_unit(String cur_unit) {
        this.cur_unit = cur_unit;
    }

    public String getKftc_deal_bas_r() {
        return kftc_deal_bas_r;
    }

    public void setKftc_deal_bas_r(String kftc_deal_bas_r) {
        this.kftc_deal_bas_r = kftc_deal_bas_r;
    }

    public int getNationimage() {
        return nationimage;
    }

    public void setNationimage(int nationimage) {
        this.nationimage = nationimage;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String getNewstimeglobal() {
        return newstimeglobal;
    }

    public void setNewstimeglobal(String newstimeglobal) {
        this.newstimeglobal = newstimeglobal;
    }

    public String getTdoayC1() {
        return tdoayC1;
    }

    public void setTdoayC1(String tdoayC1) {
        this.tdoayC1 = tdoayC1;
    }

    public String getTodayConditon() {
        return todayConditon;
    }

    public void setTodayConditon(String todayConditon) {
        this.todayConditon = todayConditon;
    }

    public String getTimedifferent() {
        return timedifferent;
    }

    public void setTimedifferent(String timedifferent) {
        this.timedifferent = timedifferent;
    }
}
