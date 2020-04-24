package exchange.example.newopenapiexchangeproject3;

public class NoteRubbishVO {
    private String RubbishDate;
    private String RubbishTitle;
    private String RubbishContent;

    public NoteRubbishVO(String rubbishDate, String rubbishTitle, String rubbishContent) {
        RubbishDate = rubbishDate;
        RubbishTitle = rubbishTitle;
        RubbishContent = rubbishContent;
    }

    public String getRubbishDate() {
        return RubbishDate;
    }

    public void setRubbishDate(String rubbishDate) {
        RubbishDate = rubbishDate;
    }

    public String getRubbishTitle() {
        return RubbishTitle;
    }

    public void setRubbishTitle(String rubbishTitle) {
        RubbishTitle = rubbishTitle;
    }

    public String getRubbishContent() {
        return RubbishContent;
    }

    public void setRubbishContent(String rubbishContent) {
        RubbishContent = rubbishContent;
    }
}
