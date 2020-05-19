package guestbook;

public class GuestbookEntryModel {

	@Override
	public String toString() {
		return "GuestbookEntry [name=" + name + ", content=" + content + "]";
	}

	private String name;
	private String content;

	public GuestbookEntryModel(String name, String content) {
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
