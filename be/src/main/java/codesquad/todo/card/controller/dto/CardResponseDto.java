package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardResponseDto {
	private Long id;
	private String title;
	private String content;
	private int position;
	private Long columnId;

	public CardResponseDto(Long id, String title, String content, int position, Long columnId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.position = position;
		this.columnId = columnId;
	}

	public static CardResponseDto from(Card card) {
		return new CardResponseDto(card.getId(), card.getTitle(), card.getContent(), card.getPosition(),
			card.getColumnId());
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
}
