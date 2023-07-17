package codesquad.todo.card.controller.dto;

import codesquad.todo.card.entity.Card;

public class CardSaveResponse {
	private CardResponseDTO card;
	private boolean success;

	public CardSaveResponse(CardResponseDTO card, boolean success) {
		this.card = card;
		this.success = success;
	}

	public CardResponseDTO getCard() {
		return card;
	}

	public boolean isSuccess() {
		return success;
	}

	public static CardSaveResponse from(Card card) {
		return new CardSaveResponse(CardResponseDTO.from(card), true);
	}

}
