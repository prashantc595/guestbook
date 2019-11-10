package guestbook;

public class GuestbookEntry {

    private String name;
    private String content;

    public GuestbookEntry(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
