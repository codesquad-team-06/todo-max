package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardSaveResponse {
	private CardSaveDTO card;
	private boolean success;

	public CardSaveResponse(CardSaveDTO card, boolean success) {
		this.card = card;
		this.success = success;
	}

	public static CardSaveResponse from(Card card) {
		return new CardSaveResponse(CardSaveDTO.from(card), true);
	}

	public CardSaveDTO getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}

}
