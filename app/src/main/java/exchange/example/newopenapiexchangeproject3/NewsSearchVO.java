package exchange.example.newopenapiexchangeproject3;

public class NewsSearchVO {
    private String newsTitle;
    private String newsTime;

    public NewsSearchVO(String newsTitle, String newsTime) {
        this.newsTitle = newsTitle;
        this.newsTime = newsTime;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }
}

