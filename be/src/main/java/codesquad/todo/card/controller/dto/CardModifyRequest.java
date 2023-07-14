package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardModifyRequest {
	private Long id;
	private String title;
	private String content;

	public CardModifyRequest() {
	}

	public CardModifyRequest(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
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

	public Card toEntity() {
		return Card.builder()
			.id(id)
			.title(title)
			.content(content)
			.build();
	}
}
