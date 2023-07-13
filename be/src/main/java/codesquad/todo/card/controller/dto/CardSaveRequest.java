package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardSaveRequest {
	String title;
	String content;
	long columnId;

	public CardSaveRequest() {
	}

	public CardSaveRequest(String title, String content, long columnId) {
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

	public Card toEntity() {
		return Card.builder()
			.title(title)
			.content(content)
			.columnId(columnId)
			.build();
	}
}
