package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardSaveDTO {
	private Long id;
	private String title;
	private String content;
	private int position;
	private Long columnId;

	public CardSaveDTO(Long id, String title, String content, int position, Long columnId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.position = position;
		this.columnId = columnId;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public int getPosition() {
		return position;
	}

	public Long getColumnId() {
		return columnId;
	}

	public static CardSaveDTO from(Card card) {
		return new CardSaveDTO(card.getId(), card.getTitle(), card.getContent(), card.getPosition(),
			card.getColumnId());
	}
}
