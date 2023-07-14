package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardDeleteResponse {
	private Long cardId;
	private boolean success;

	public CardDeleteResponse(Long cardId, boolean success) {
		this.cardId = cardId;
		this.success = success;
	}

	public Long getCardId() {
		return cardId;
	}

	public boolean isSuccess() {
		return success;
	}

	public static CardDeleteResponse from(Card card) {
		return new CardDeleteResponse(card.getId(), true);
	}
}
