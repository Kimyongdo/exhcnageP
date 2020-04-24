package exchange.example.newopenapiexchangeproject3;

import android.widget.TextView;

import java.io.Serializable;

public class NewsKeywordVO implements Serializable {

    private String keywrod;

    public NewsKeywordVO(String keywrod) {
        this.keywrod = keywrod;
    }

    public String getKeywrod() {
        return keywrod;
    }

    public void setKeywrod(String keywrod) {
        this.keywrod = keywrod;
    }
}
