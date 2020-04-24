package exchange.example.newopenapiexchangeproject3;

public class UpdateVO {

  private String UpdateTtitle;
  private String UpdateName;
  private String UpdateContent;
  private String UpdateTime;

    public UpdateVO(String updateTtitle, String updateName, String updateContent, String updateTime) {
        UpdateTtitle = updateTtitle;
        UpdateName = updateName;
        UpdateContent = updateContent;
        UpdateTime = updateTime;
    }

    public String getUpdateTtitle() {
        return UpdateTtitle;
    }

    public void setUpdateTtitle(String updateTtitle) {
        UpdateTtitle = updateTtitle;
    }

    public String getUpdateName() {
        return UpdateName;
    }

    public void setUpdateName(String updateName) {
        UpdateName = updateName;
    }

    public String getUpdateContent() {
        return UpdateContent;
    }

    public void setUpdateContent(String updateContent) {
        UpdateContent = updateContent;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
