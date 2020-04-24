package exchange.example.newopenapiexchangeproject3;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class Itemlist_nation implements Serializable {

    private String tv;
    private int iv;       //이미지는  int로 받는다. 여기서 계속 ImageView로 썼음..

    public Itemlist_nation(String tv, int iv) {
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
