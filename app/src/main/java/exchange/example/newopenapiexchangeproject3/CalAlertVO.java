package exchange.example.newopenapiexchangeproject3;

public class CalAlertVO {

    private String tv;
    private int iv;       //이미지는  int로 받는다. 여기서 계속 ImageView로 썼음..

    public CalAlertVO(String tv, int iv) {
        this.tv = tv;
        this.iv = iv;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public int getIv() {
        return iv;
    }

    public void setIv(int iv) {
        this.iv = iv;
    }
}
