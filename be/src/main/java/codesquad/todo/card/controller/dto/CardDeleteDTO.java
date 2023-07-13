package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardDeleteDTO {
	private Long id;
	private boolean isDeleted;

	public CardDeleteDTO(Long id, boolean isDeleted) {
		this.id = id;
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public static CardDeleteDTO from(Card card) {
		return new CardDeleteDTO(card.getId(), card.isDeleted());
	}
}
