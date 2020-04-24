package exchange.example.newopenapiexchangeproject3.NaverSearchApi;

import java.util.List;

public class SearchApi {
    private String lastBuildDate;   // Json으로 들어오는 시 들어는 문자와 String 변숨여이 똑같아야한다. 중요!
    private String total;
    private String start;
    private String display;
    private List<NEWS> items;

    public SearchApi(String lastBuildDate, String total, String start, String display, List<NEWS> items) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List<NEWS> getItems() {
        return items;
    }

    public void setItems(List<NEWS> items) {
        this.items = items;
    }
}
