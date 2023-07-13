package codesquad.todo.card.controller.dto;

public class CardCreateRequest {
	String title;
	String content;
	long columnId;

	public CardCreateRequest(String title,String content,long columnId){
		this.title = title;
		this.content = content;
		this.columnId = columnId;
	}
	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public long getColumnId() {
		return columnId;
	}
}
