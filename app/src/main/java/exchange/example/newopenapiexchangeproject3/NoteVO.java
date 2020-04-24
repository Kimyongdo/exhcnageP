package exchange.example.newopenapiexchangeproject3;

public class NoteVO {
    private String NoteDate;
    private String NoteTitle;
    private String NoteContent;

    public NoteVO(String noteDate, String noteTitle, String noteContent) {
        NoteDate = noteDate;
        NoteTitle = noteTitle;
        NoteContent = noteContent;
    }


    public String getNoteDate() {
        return NoteDate;
    }

    public void setNoteDate(String noteDate) {
        NoteDate = noteDate;
    }

    public String getNoteTitle() {
        return NoteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        NoteTitle = noteTitle;
    }

    public String getNoteContent() {
        return NoteContent;
    }

    public void setNoteContent(String noteContent) {
        NoteContent = noteContent;
    }
}
