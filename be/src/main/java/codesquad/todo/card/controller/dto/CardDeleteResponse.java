package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardDeleteResponse {
	private CardDeleteDTO card;
	private boolean success;

	public CardDeleteResponse(CardDeleteDTO card, boolean success) {
		this.card = card;
		this.success = success;
	}

	public CardDeleteDTO getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}

	public static CardDeleteResponse from(Card card) {
		return new CardDeleteResponse(CardDeleteDTO.from(card), true);
	}
}
